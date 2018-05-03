/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.database;

import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.JsonFilterService;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceColumn;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.database.DatabaseDataSource;
import com.abixen.platform.service.businessintelligence.multivisualisation.infrastructure.exception.DataParsingException;
import com.abixen.platform.service.businessintelligence.multivisualisation.infrastructure.exception.DataSourceValueException;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;


abstract class AbstractDatabaseService {


    @Autowired
    private JsonFilterService jsonFilterService;

    public List<DataSourceColumn> findColumns(Connection connection, String tableName) {

        List<DataSourceColumn> columns = new ArrayList<>();

        try {
            ResultSetMetaData rsmd = getDatabaseMetaData(connection, tableName);

            int columnCount = rsmd.getColumnCount();

            IntStream.range(1, columnCount + 1).forEach(i -> {
                try {
                    columns.add(prepareDataSourceColumns(rsmd, i));
                } catch (SQLException e) {
                    throw new PlatformRuntimeException(e);
                }
            });

        } catch (SQLException e) {
            throw new PlatformRuntimeException(e);
        }

        return columns;
    }

    private DataSourceColumn prepareDataSourceColumns(ResultSetMetaData rsmd, int i) throws SQLException {
        DataValueType dataValueType = DataValueType.valueOf(getValidColumnTypeName(i, rsmd));
        String name = rsmd.getColumnName(i);
        return DataSourceColumn.builder()
                .details(name)
                .parameters(dataValueType, i)
                .build();
    }

    private ResultSetMetaData getDatabaseMetaData(Connection connection, String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        //FixMe
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
        return rs.getMetaData();
    }

    public List<String> findTables(Connection connection) {
        final int objectTypeIndex = 4;
        final int objectNameIndex = 3;

        List<String> tables = new ArrayList<>();

        try {
            ResultSet rs = getTablesAsResultSet(connection);

            while (rs.next()) {
                if ("TABLE".equals(rs.getString(objectTypeIndex)) || "VIEW".equals(rs.getString(objectTypeIndex))) {
                    tables.add(rs.getString(objectNameIndex).toUpperCase());
                }
            }

        } catch (SQLException e) {
            throw new PlatformRuntimeException(e);
        }

        return tables;
    }

    protected ResultSet getTablesAsResultSet(Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();

        return md.getTables(null, null, "%", null);
    }

    public List<Map<String, DataValueDto>> findChartData(Connection connection,
                                                         DataSource dataSource,
                                                         ChartConfigurationForm chartConfigurationForm,
                                                         String seriesName) {
        return seriesName != null ? getChartDataPreview(connection, (DatabaseDataSource) dataSource, chartConfigurationForm, seriesName)
                : getChartData(connection, (DatabaseDataSource) dataSource, chartConfigurationForm);
    }

    private List<Map<String, DataValueDto>> getChartData(Connection connection, DatabaseDataSource databaseDataSource, ChartConfigurationForm chartConfigurationForm) {
        final Set<String> chartColumnsSet = getDomainColumn(chartConfigurationForm);

        chartConfigurationForm.getDataSet().getDataSetSeries().forEach(dataSetSeries -> {
            if (dataSetSeries.getValueSeriesColumn().getDataSourceColumn() != null) {
                chartColumnsSet.add(dataSetSeries.getValueSeriesColumn().getDataSourceColumn().getName());
            }
        });

        if (chartColumnsSet.isEmpty()) {
            return new ArrayList<>();
        }

        return getData(connection, databaseDataSource, chartColumnsSet, chartConfigurationForm, null);
    }

    private Set<String> getDomainColumn(ChartConfigurationForm chartConfigurationForm) {
        final Set<String> chartColumnsSet = new HashSet<>();
        if (chartConfigurationForm.getDataSet().getDomainXSeriesColumn() != null) {
            chartColumnsSet.add(chartConfigurationForm.getDataSet().getDomainXSeriesColumn().getDataSourceColumn().getName());
        }

        if (chartConfigurationForm.getDataSet().getDomainZSeriesColumn() != null) {
            chartColumnsSet.add(chartConfigurationForm.getDataSet().getDomainZSeriesColumn().getDataSourceColumn().getName());
        }

        return chartColumnsSet;
    }

    private List<Map<String, DataValueDto>> getChartDataPreview(Connection connection, DatabaseDataSource databaseDataSource, ChartConfigurationForm chartConfigurationForm, String seriesName) {
        final int chartLimit = 8;
        final Set<String> chartColumnsSet = getDomainColumn(chartConfigurationForm);

        chartConfigurationForm.getDataSet().getDataSetSeries().forEach(dataSetSeries -> {
            if (dataSetSeries.getName().equals(seriesName)) {
                chartColumnsSet.add(dataSetSeries.getValueSeriesColumn().getDataSourceColumn().getName());
            }
        });

        if (chartColumnsSet.isEmpty()) {
            return new ArrayList<>();
        }

        return getData(connection, databaseDataSource, chartColumnsSet, chartConfigurationForm, chartLimit);
    }

    public List<Map<String, DataValueDto>> findDataSourcePreview(Connection connection, DataSource dataSource) {
        final int datasourceLimit = 20;
        final Set<String> dataSourceColumnsSet = new HashSet<>();

        dataSource.getColumns().forEach(column -> dataSourceColumnsSet.add(column.getName()));

        if (dataSourceColumnsSet.isEmpty()) {
            return new ArrayList<>();
        }

        return getData(connection, (DatabaseDataSource) dataSource, dataSourceColumnsSet, null, datasourceLimit);
    }

    private List<Map<String, DataValueDto>> getData(Connection connection, DatabaseDataSource databaseDataSource, Set<String> chartColumnsSet, ChartConfigurationForm chartConfigurationForm, Integer limit) {
        ResultSet rs;
        List<Map<String, DataValueDto>> data = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSetMetaData resultSetMetaData = getDatabaseMetaData(connection, databaseDataSource.getTable());
            if (chartConfigurationForm != null) {
                rs = statement.executeQuery(buildQueryForChartData(databaseDataSource, chartColumnsSet, resultSetMetaData, chartConfigurationForm, limit));
            } else {
                rs = statement.executeQuery(buildQueryForDataSourceData(databaseDataSource, chartColumnsSet, resultSetMetaData, limit).toString());
            }
            while (rs.next()) {
                final ResultSet row = rs;
                Map<String, DataValueDto> rowMap = new HashMap<>();
                chartColumnsSet.forEach(chartColumnsSetElement -> rowMap.put(chartColumnsSetElement, getDataFromColumn(row, chartColumnsSetElement)));
                data.add(rowMap);
            }
        } catch (SQLException e) {
            throw new DataParsingException("Error when parsing data from db. " + e.getMessage());
        }
        return data;
    }

    private String buildQueryForChartData(DatabaseDataSource databaseDataSource, Set<String> chartColumnsSet, ResultSetMetaData resultSetMetaData, ChartConfigurationForm chartConfigurationForm, Integer limit) throws SQLException {
        StringBuilder outerSelect = new StringBuilder("SELECT ");
        outerSelect.append("subQueryResult." + chartColumnsSet.toString().substring(1, chartColumnsSet.toString().length() - 1));
        outerSelect.append(" FROM (");
        outerSelect.append(buildQueryForDataSourceData(databaseDataSource, chartColumnsSet, resultSetMetaData, limit));
        outerSelect.append(") subQueryResult WHERE ");
        outerSelect.append(jsonFilterService.convertJsonToJpql(chartConfigurationForm.getFilter(), resultSetMetaData));
        return outerSelect.toString();
    }

    private StringBuilder buildQueryForDataSourceData(DatabaseDataSource databaseDataSource, Set<String> chartColumnsSet, ResultSetMetaData resultSetMetaData, Integer limit) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        stringBuilder.append(chartColumnsSet.toString().substring(1, chartColumnsSet.toString().length() - 1));
        stringBuilder.append(" FROM ");
        stringBuilder.append(databaseDataSource.getTable());
        stringBuilder.append(" WHERE ");
        stringBuilder.append(jsonFilterService.convertJsonToJpql(databaseDataSource.getFilter(), resultSetMetaData));
        return limit != null ? setLimitConditionForCharDataQuery(stringBuilder, limit) : stringBuilder;
    }

    protected StringBuilder setLimitConditionForCharDataQuery(StringBuilder stringBuilder, Integer limit) {
        stringBuilder.append(" LIMIT ");
        stringBuilder.append(limit + " ");
        return stringBuilder;
    }

    private DataValueDto getDataFromColumn(ResultSet row, String columnName) {
        try {
            ResultSetMetaData resultSetMetaData = row.getMetaData();
            String columnTypeName = getValidColumnTypeName(row.findColumn(columnName), resultSetMetaData);
            return getValueAsDataSourceValue(row, columnName, DataValueType.valueOf(columnTypeName));
        } catch (SQLException e) {
            throw new DataSourceValueException("Error when getting value from column. " + e.getMessage());
        }
    }

    private String getValidColumnTypeName(Integer columnIndex, ResultSetMetaData resultSetMetaData) throws SQLException {
        String columnTypeName = resultSetMetaData.getColumnTypeName(columnIndex).toUpperCase();
        Map<String, String> databaseTypeOnApplicationType = buildDatabaseTypeOnApplicationType();
        String mappedColumnType = databaseTypeOnApplicationType.get(columnTypeName);
        if (mappedColumnType != null) {
            return mappedColumnType;
        }
        return columnTypeName;
    }

    private Map<String, String> buildDatabaseTypeOnApplicationType() {
        return getNativeTypeMapping(getStandardTypeMapping());
    }

    protected Map<String, String> getNativeTypeMapping(Map<String, String> databaseTypeOnApplicationType) {
        return databaseTypeOnApplicationType;
    }

    private Map<String, String> getStandardTypeMapping() {
        Map<String, String> databaseTypeOnApplicationType = new HashMap<>();
        databaseTypeOnApplicationType.put("BIGINT", DataValueType.INTEGER.getName());
        databaseTypeOnApplicationType.put("VARCHAR", DataValueType.STRING.getName());
        databaseTypeOnApplicationType.put("FLOAT8", DataValueType.DOUBLE.getName());
        databaseTypeOnApplicationType.put("DECIMAL", DataValueType.DOUBLE.getName());
        return databaseTypeOnApplicationType;
    }


    private DataValueDto getValueAsDataSourceValue(ResultSet row, String columnName, DataValueType columnTypeName) throws SQLException {
        switch (columnTypeName) {
            case DOUBLE:
                return getValueAsDataSourceValueDoubleWeb(row, columnName);
            case DATE:
                return getValueAsDataSourceValueDateWeb(row, columnName);
            case INTEGER:
                return getValueAsDataSourceValueIntegerWeb(row, columnName);
            case STRING:
                return getValueAsDataSourceValueStringWeb(row, columnName);
            default:
                throw new NotImplementedException("Not recognized column type.");
        }
    }

    private DataValueDto getValueAsDataSourceValueDateWeb(ResultSet row, String columnName) throws SQLException {
        Date value = row.getDate(row.findColumn(columnName));
        return new DataValueDto<Date>()
                .setValue(value);
    }

    private DataValueDto getValueAsDataSourceValueDoubleWeb(ResultSet row, String columnName) throws SQLException {
        Double value = row.getDouble(row.findColumn(columnName));
        return new DataValueDto<Double>()
                .setValue(value);
    }

    private DataValueDto getValueAsDataSourceValueIntegerWeb(ResultSet row, String columnName) throws SQLException {
        Integer value = row.getInt(row.findColumn(columnName));
        return new DataValueDto<Integer>()
                .setValue(value);
    }

    private DataValueDto getValueAsDataSourceValueStringWeb(ResultSet row, String columnName) throws SQLException {
        String value = row.getString(row.findColumn(columnName));
        return new DataValueDto<String>()
                .setValue(value);
    }
}
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

package com.abixen.platform.module.chart.service.impl;

import com.abixen.platform.module.chart.exception.DataParsingException;
import com.abixen.platform.module.chart.exception.DataSourceValueException;
import com.abixen.platform.module.chart.form.ChartConfigurationForm;
import com.abixen.platform.module.chart.model.enumtype.DataValueType;
import com.abixen.platform.module.chart.model.impl.DatabaseDataSource;
import com.abixen.platform.module.chart.model.web.*;
import org.apache.commons.lang3.NotImplementedException;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.IntStream;


public abstract class AbstractDatabaseService {

    private static final int LIMIT = 6;

    public List<String> getColumns(Connection connection, String tableName) {

        List<String> columns = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();

            IntStream.range(1, columnCount + 1).forEach(i -> {
                try {
                    columns.add(rsmd.getColumnName(i));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columns;
    }

    public List<String> getTables(Connection connection) {

        final int objectTypeIndex = 4;
        final int objectNameIndex = 3;

        List<String> tables = new ArrayList<>();

        try {
            ResultSet rs = getTablesAsResultSet(connection);

            while (rs.next()) {
                if ("TABLE".equals(rs.getString(objectTypeIndex)) || "VIEW".equals(rs.getString(objectTypeIndex))) {
                    if (isAllowedTable(rs.getString(objectNameIndex))) {
                        tables.add(rs.getString(objectNameIndex));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    protected ResultSet getTablesAsResultSet(Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        return md.getTables(null, null, "%", null);
    }

    protected boolean isAllowedTable(String tableName) {
        return true;
    }

    public List<Map<String, DataSourceValueWeb>> getChartData(Connection connection, DatabaseDataSource databaseDataSource, ChartConfigurationForm chartConfigurationForm) {
        Set<String> chartColumnsSet = getDomainColumn(chartConfigurationForm);

        chartConfigurationForm.getDataSetChart().getDataSetSeries().forEach(dataSetSeries -> {
            if (dataSetSeries.getValueSeriesColumn().getDataSourceColumn() != null) {
                chartColumnsSet.add(dataSetSeries.getValueSeriesColumn().getDataSourceColumn().getName());
            }
        });

        if (chartColumnsSet.isEmpty()) {
            return new ArrayList<>();
        }
        return getData(connection, databaseDataSource, chartColumnsSet);
    }

    private Set<String> getDomainColumn(ChartConfigurationForm chartConfigurationForm) {
        Set<String> chartColumnsSet = new HashSet<>();
        if (chartConfigurationForm.getDataSetChart().getDomainXSeriesColumn() != null) {
            chartColumnsSet.add(chartConfigurationForm.getDataSetChart().getDomainXSeriesColumn().getDataSourceColumn().getName());
        }

        if (chartConfigurationForm.getDataSetChart().getDomainZSeriesColumn() != null) {
            chartColumnsSet.add(chartConfigurationForm.getDataSetChart().getDomainZSeriesColumn().getDataSourceColumn().getName());
        }
        return chartColumnsSet;
    }

    public List<Map<String, DataSourceValueWeb>> getChartDataPreview(Connection connection, DatabaseDataSource databaseDataSource, ChartConfigurationForm chartConfigurationForm, String seriesName) {
        Set<String> chartColumnsSet = getDomainColumn(chartConfigurationForm);

        chartConfigurationForm.getDataSetChart().getDataSetSeries().forEach(dataSetSeries -> {
            if (dataSetSeries.getName().equals(seriesName)) {
                chartColumnsSet.add(dataSetSeries.getValueSeriesColumn().getDataSourceColumn().getName());
            }
        });

        if (chartColumnsSet.isEmpty()) {
            return new ArrayList<>();
        }
        return getData(connection, databaseDataSource, chartColumnsSet).subList(0, LIMIT);
    }

    private List<Map<String, DataSourceValueWeb>> getData(Connection connection, DatabaseDataSource databaseDataSource, Set<String> chartColumnsSet) {
        ResultSet rs;
        List<Map<String, DataSourceValueWeb>> data = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(buildQueryForChartData(databaseDataSource, chartColumnsSet));

            if (rs != null) {
                while (rs.next()) {
                    final ResultSet row = rs;
                    Map<String, DataSourceValueWeb> rowMap = new HashMap<>();
                    chartColumnsSet.forEach(chartColumnsSetElement -> {
                         rowMap.put(chartColumnsSetElement, getDataFromColumn(row, chartColumnsSetElement));
                    });
                    data.add(rowMap);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataParsingException("Error when parsing data from db. " + e.getMessage());
        }
        return data;
    }

    private String buildQueryForChartData(DatabaseDataSource databaseDataSource, Set<String> chartColumnsSet) {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        stringBuilder.append(chartColumnsSet.toString().substring(1, chartColumnsSet.toString().length() - 1));
        stringBuilder.append(" FROM ");
        stringBuilder.append(databaseDataSource.getTable());
        return stringBuilder.toString();
    }

    private DataSourceValueWeb getDataFromColumn(ResultSet row, String columnName) {
        try {
            ResultSetMetaData resultSetMetaData = row.getMetaData();
            String columnTypeName = resultSetMetaData.getColumnTypeName(row.findColumn(columnName));
            if ("BIGINT".equals(columnTypeName)) {
                columnTypeName = "INTEGER";
            }
            if ("VARCHAR".equals(columnTypeName)) {
                columnTypeName = "STRING";
            }
            return getValueAsDataSourceValue(row, columnName, DataValueType.valueOf(columnTypeName));
        } catch (SQLException e) {
            throw new DataSourceValueException("Error when getting value from column. " + e.getMessage());
        }
    }

    private DataSourceValueWeb getValueAsDataSourceValue(ResultSet row, String columnName, DataValueType columnTypeName) throws SQLException {
        switch (columnTypeName) {
            case DOUBLE:
                return getValueAsDataSourceValueDoubleWeb(row, columnName);
            case DATE:
                return getValueAsDataSourceValueDateWeb(row, columnName);
            case INTEGER:
                return getValueAsDataSourceValueIntegerWeb(row, columnName);
            case STRING:
                return getValueAsDataSourceValueStringWeb(row, columnName);
            default: throw new NotImplementedException("Not recognized column type.");
        }
    }

    private DataSourceValueWeb getValueAsDataSourceValueDateWeb(ResultSet row, String columnName) throws SQLException {
        Date value = row.getDate(row.findColumn(columnName));
        return new DataSourceValueDateWeb() {
            @Override
            public Date getValue() {
                return value;
            }

            @Override
            public void setValue(Date value) {
                throw new NotImplementedException("Setter not implemented yet");
            }
        };
    }

    private DataSourceValueWeb getValueAsDataSourceValueDoubleWeb(ResultSet row, String columnName) throws SQLException {
        Double value = row.getDouble(row.findColumn(columnName));
        return new DataSourceValueDoubleWeb() {
            @Override
            public Double getValue() {
                return value;
            }

            @Override
            public void setValue(Double value) {
                throw new NotImplementedException("Setter not implemented yet");
            }
        };
    }

    private DataSourceValueWeb getValueAsDataSourceValueIntegerWeb(ResultSet row, String columnName) throws SQLException {
        Integer value = row.getInt(row.findColumn(columnName));
        return new DataSourceValueIntegerWeb() {
            @Override
            public Integer getValue() {
                return value;
            }

            @Override
            public void setValue(Integer value) {
                throw new NotImplementedException("Setter not implemented yet");
            }
        };
    }

    private DataSourceValueWeb getValueAsDataSourceValueStringWeb(ResultSet row, String columnName) throws SQLException {
        String value = row.getString(row.findColumn(columnName));
        return new DataSourceValueStringWeb() {
            @Override
            public String getValue() {
                return value;
            }

            @Override
            public void setValue(String value) {
                throw new NotImplementedException("Setter not implemented yet");
            }
        };
    }
}
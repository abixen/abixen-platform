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

package com.abixen.platform.businessintelligence.chart.service.impl;

import com.abixen.platform.core.util.ModelKeys;
import com.abixen.platform.businessintelligence.chart.dto.DataSourceCsvParametersDTO;
import com.abixen.platform.businessintelligence.chart.model.enumtype.ColumnType;
import com.abixen.platform.businessintelligence.chart.model.enumtype.DataOrientation;
import com.abixen.platform.businessintelligence.chart.model.enumtype.DataSourceFileType;
import com.abixen.platform.businessintelligence.chart.model.enumtype.DataValueType;
import com.abixen.platform.businessintelligence.chart.model.impl.*;
import com.abixen.platform.businessintelligence.chart.repository.DataSetRepository;
import com.abixen.platform.businessintelligence.chart.repository.FileDataSourceRepository;
import com.abixen.platform.businessintelligence.chart.service.DataSourceImportDataService;
import com.abixen.platform.businessintelligence.chart.service.DomainBuilderService;
import com.abixen.platform.businessintelligence.chart.util.*;
import com.abixen.platform.businessintelligence.configuration.PlatformModuleConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformModuleConfiguration.class)
public class DataSourceImportDataServiceCsvImplTest {


    public DomainBuilderService domainBuilderService = new DomainBuilderServiceImpl();


    @Resource
    public FileDataSourceRepository fileDataSourceRepository;

    @Resource
    public DataSetRepository dataSetRepository;

    private DataSourceCsvParametersDTO dataSourceCsvParametersDto;

    //TODO - missing generic type
    @Autowired
    public DataSourceImportDataService dataSourceImportDataServiceCsv;

    private static Validator validator;


    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Before
    @SuppressWarnings("unchecked")
    public void setup() throws IOException {

        fileDataSourceRepository.deleteAllInBatch();
        MockitoAnnotations.initMocks(this);
        File dataFile = File.createTempFile("tst", "temp0");
        FileWriter fileWriter = new FileWriter(dataFile);
        fileWriter.write("0,1,2,3,4,5,6\n");
        fileWriter.write("0,10,20,30,40,50,60\n");
        fileWriter.write("0,100,200,300,400,500,600\n");
        fileWriter.flush();

        FileDataSource ds = domainBuilderService.newFileDataSourceBuilderInstance().base("testCSV", "testCSV", DataSourceFileType.CSV).build();

        fileDataSourceRepository.save(ds);

        dataSourceCsvParametersDto = new DataSourceCsvParametersDTO();
        dataSourceCsvParametersDto.setName("testName");
        dataSourceCsvParametersDto.setSeparator(ModelKeys.COMMA);
        dataSourceCsvParametersDto.setOrientation(DataOrientation.Rows);

        List<DataValueType> dataValueTypes = new ArrayList<>(7);

        dataValueTypes.add(0, DataValueType.DOUBLE);
        dataValueTypes.add(1, DataValueType.DOUBLE);
        dataValueTypes.add(2, DataValueType.DOUBLE);
        dataValueTypes.add(3, DataValueType.DOUBLE);
        dataValueTypes.add(4, DataValueType.DOUBLE);
        dataValueTypes.add(5, DataValueType.DOUBLE);
        dataValueTypes.add(6, DataValueType.DOUBLE);

        dataSourceCsvParametersDto.setValueTypes(dataValueTypes);
        Map<String, Integer> columns = new HashMap<>(6);
        columns.put("column1", 0);
        columns.put("column2", 1);
        columns.put("column3", 2);
        columns.put("column4", 3);
        columns.put("column5", 4);
        columns.put("column6", 5);
        columns.put("column7", 6);
        dataSourceCsvParametersDto.setColumns(columns);
        dataSourceCsvParametersDto.setFileDataSource(ds);
        dataSourceCsvParametersDto.setCsvFile(dataFile);

        log.debug("dataSourceCsvParametersDTO:" + dataSourceCsvParametersDto.toString());
    }


    @Test
    @Transactional
    public void testValidationDataSource() throws Exception {
        DataSourceCsvParametersDTO dataSourceCsvParametersDTOfail = new DataSourceCsvParametersDTO();
        Set<ConstraintViolation<DataSourceCsvParametersDTO>> constraintViolations = validator.validate(dataSourceCsvParametersDTOfail);
        assertNotEquals(0, constraintViolations.size());
    }

    @Test
    @Transactional
    public void testInitSeries() {
        log.debug("Start test initialization of series.");
        dataSourceImportDataServiceCsv.init(dataSourceCsvParametersDto);
        Boolean testVerify = dataSourceImportDataServiceCsv.verify();
        assertTrue(testVerify);
        FileDataSource fileDataSource = dataSourceImportDataServiceCsv.saveData();
        Set<DataSourceColumn> dataSourceColumns = fileDataSource.getColumns();
        assertNotNull(fileDataSource);
        assertEquals(dataSourceColumns.size(), 7);

        log.debug("Finish test initialization of series.");
    }

    @Test
    @Transactional
    public void testDataSet() throws IOException, IllegalAccessException, InstantiationException {

        fillDataSource("datasource_test");

        DataSourceColumn dataSourceColumnX = null;
        List<DataSourceColumn> dataSourceColumnsY = new ArrayList<DataSourceColumn>();

        //get existed data source
        FileDataSource fileDataSource = fileDataSourceRepository.findByName("datasource_test");

        Set<DataSourceColumn> dataSourceColumns = fileDataSource.getColumns();
        for (DataSourceColumn dataSourceColumn : dataSourceColumns) {
            if (dataSourceColumn.getName().equalsIgnoreCase("column1")) {
                dataSourceColumnX = dataSourceColumn;
            } else {
                dataSourceColumnsY.add(dataSourceColumn);
            }
        }

        //create data set
        DataSetBuilder dataSetBuilder = domainBuilderService.newDataSetBuilderInstance();
        DataSet dataSet = dataSetBuilder.create().build();

        //Attach data source to data set
        //dataSet.setDataSource(fileDataSource);

        //Create  DataSetSeries 1
        DataSetSeriesBuilder dataSetSeriesBuilder = domainBuilderService.newDataSetSeriesBuilderInstance();
        DataSetSeries dataSetSeries1 = dataSetSeriesBuilder.name("series1").dataSet(dataSet).create().build();

        dataSetSeriesBuilder = domainBuilderService.newDataSetSeriesBuilderInstance();
        DataSetSeries dataSetSeries2 = dataSetSeriesBuilder.name("series2").dataSet(dataSet).create().build();

        //DataSetSeriesColumn
        DataSetSeriesColumnBuilder dataSetSeriesColumnBuilder = domainBuilderService.newDataSetSeriesColumnBuilderInstance();
        dataSetSeriesColumnBuilder.columnType(ColumnType.X);
        dataSetSeriesColumnBuilder.dataSourceColumn(dataSourceColumnX);
        dataSetSeriesColumnBuilder.name("ColumnX");
        DataSetSeriesColumn dataColumnX = dataSetSeriesColumnBuilder.build();

        dataSetSeriesColumnBuilder = domainBuilderService.newDataSetSeriesColumnBuilderInstance();
        dataSetSeriesColumnBuilder.columnType(ColumnType.Z);
        dataSetSeriesColumnBuilder.dataSourceColumn(dataSourceColumnX);
        dataSetSeriesColumnBuilder.name("ColumnZ");
        DataSetSeriesColumn dataColumnZ = dataSetSeriesColumnBuilder.build();

        dataSetSeriesColumnBuilder = domainBuilderService.newDataSetSeriesColumnBuilderInstance();
        dataSetSeriesColumnBuilder.columnType(ColumnType.Y);
        dataSetSeriesColumnBuilder.name("series1ColumnY1");
        dataSetSeriesColumnBuilder.dataSourceColumn(dataSourceColumnsY.get(0));
        DataSetSeriesColumn dataSetSeries1ColumnY1 = dataSetSeriesColumnBuilder.build();


        dataSetSeriesColumnBuilder = domainBuilderService.newDataSetSeriesColumnBuilderInstance();
        dataSetSeriesColumnBuilder.columnType(ColumnType.Y);
        dataSetSeriesColumnBuilder.name("series1ColumnY2");
        dataSetSeriesColumnBuilder.dataSourceColumn(dataSourceColumnsY.get(1));
        DataSetSeriesColumn dataSetSeries1ColumnY2 = dataSetSeriesColumnBuilder.build();

        //Attach series columns to data set series1
        dataSetSeries1.setValueSeriesColumn(dataSetSeries1ColumnY1);

        //Attach series columns to data set series2
        dataSetSeries2.setValueSeriesColumn(dataSetSeries1ColumnY2);

        //Attach data set series to data set
        Set<DataSetSeries> dataSetSeries = new HashSet<>();

        dataSetSeries.add(dataSetSeries1);
        dataSetSeries.add(dataSetSeries2);

        dataSet.setDomainXSeriesColumn(dataColumnX);
        dataSet.setDomainZSeriesColumn(dataColumnZ);
        dataSet.setDataSetSeries(dataSetSeries);

        dataSet = dataSetRepository.saveAndFlush(dataSet);

        assertNotNull(dataSet);
        assertEquals(dataSet.getDataSetSeries().size(), 2);

        assertNotNull(dataSet.getDomainXSeriesColumn());
        assertNotNull(dataSet.getDomainXSeriesColumn());

        for (DataSetSeries series : dataSet.getDataSetSeries()) {
            if (series.getName().equals("series1")) {
                DataSetSeriesColumn dataSetSeriesColumnY = series.getValueSeriesColumn();
                assertNotNull(dataSetSeriesColumnY);
            }
            if (series.getName().equals("series2")) {
                DataSetSeriesColumn dataSetSeriesColumnY = series.getValueSeriesColumn();
                assertNotNull(dataSetSeriesColumnY);
            }
        }

    }

    @Test
    @Transactional
    public void testDataSource() throws IOException, IllegalAccessException, InstantiationException {
        fillDataSource("testDataSource");

        FileDataSource fileDataSourceTest = fileDataSourceRepository.findByName("testDataSource");
        assertNotNull(fileDataSourceTest);

        assertTrue(fileDataSourceTest.getColumns().size() == 3);
        Stream<DataSourceColumn> dataSourceColumnStream = fileDataSourceTest.getColumns().stream().filter(i -> i.getPosition() == 1);
        Optional<DataSourceColumn> dataSourceColumn = dataSourceColumnStream.findFirst();
        assertTrue(dataSourceColumn.isPresent());

        DataSourceColumnFile dataSourceColumn1 = (DataSourceColumnFile) dataSourceColumn.get();
        assertTrue(dataSourceColumn1.getName().equals("column2"));
        List<DataSourceValue> dataSourceValues = dataSourceColumn1.getValues();
        assertTrue(dataSourceValues.size() == 2);
        DataSourceValue dataSourceValue = dataSourceValues.get(0);
        assertEquals(dataSourceValue.getValue(), Double.valueOf(10d));
        dataSourceValue = dataSourceValues.get(1);
        assertEquals(dataSourceValue.getValue(), Double.valueOf(20d));
    }

    private void fillDataSource(String name) {
        fileDataSourceRepository.deleteAllInBatch();

        FileDataSourceBuilder fileDataSourceBuilder = domainBuilderService.newFileDataSourceBuilderInstance();

        FileDataSource fileDataSource = fileDataSourceBuilder.base(name, "test", DataSourceFileType.CSV).build();

        DataValueType dataValueType = DataValueType.DOUBLE;

        //DataSourceValue
        //values1
        List<DataSourceValue> dataSourceValues1 = new ArrayList<>();
        DataSourceValueBuilder dataSourceValueBuilder = domainBuilderService.newDataSourceValueBuilderInstance(dataValueType);
        DataSourceValue dataSourceValue1 = dataSourceValueBuilder.position(1).value(1d).build();
        dataSourceValues1.add(dataSourceValue1);
        dataSourceValue1 = dataSourceValueBuilder.position(2).value(2d).build();
        dataSourceValues1.add(dataSourceValue1);
        //values2
        List<DataSourceValue> dataSourceValues2 = new ArrayList<>();
        DataSourceValueBuilder dataSourceValueBuilder2 = domainBuilderService.newDataSourceValueBuilderInstance(dataValueType);
        DataSourceValue dataSourceValue2 = dataSourceValueBuilder2.position(1).value(10d).build();
        dataSourceValues2.add(dataSourceValue2);
        dataSourceValue2 = dataSourceValueBuilder.position(2).value(20d).build();
        dataSourceValues2.add(dataSourceValue2);
        //values3
        List<DataSourceValue> dataSourceValues3 = new ArrayList<>();
        DataSourceValueBuilder dataSourceValueBuilder3 = domainBuilderService.newDataSourceValueBuilderInstance(dataValueType);
        DataSourceValue dataSourceValue3 = dataSourceValueBuilder2.position(1).value(100d).build();
        dataSourceValues3.add(dataSourceValue3);
        dataSourceValue3 = dataSourceValueBuilder3.position(2).value(200d).build();
        dataSourceValues3.add(dataSourceValue3);

        //DataSourceColumn
        //attach DataSourceValue column1 - value1
        DataSourceColumnFileBuilder dataSourceColumnFileBuilder = domainBuilderService.newDataSourceColumnFileBuilderInstance();
        dataSourceColumnFileBuilder = (DataSourceColumnFileBuilder) dataSourceColumnFileBuilder.position(0);
        dataSourceColumnFileBuilder = (DataSourceColumnFileBuilder) dataSourceColumnFileBuilder.name("column1");
        dataSourceColumnFileBuilder = dataSourceColumnFileBuilder.dataSource(fileDataSource);
        dataSourceColumnFileBuilder = dataSourceColumnFileBuilder.dataSourceValue(dataSourceValues1).create();
        DataSourceColumnFile dataSourceColumnFile1 = dataSourceColumnFileBuilder.build();

        //column2
        //attach DataSourceValue column2 - value2
        DataSourceColumnFileBuilder dataSourceColumnFileBuilder2 = domainBuilderService.newDataSourceColumnFileBuilderInstance();
        dataSourceColumnFileBuilder2 = (DataSourceColumnFileBuilder) dataSourceColumnFileBuilder2.position(1);
        dataSourceColumnFileBuilder2 = (DataSourceColumnFileBuilder) dataSourceColumnFileBuilder2.name("column2");
        dataSourceColumnFileBuilder2 = dataSourceColumnFileBuilder2.dataSource(fileDataSource);
        dataSourceColumnFileBuilder2 = dataSourceColumnFileBuilder2.dataSourceValue(dataSourceValues2).create();
        DataSourceColumnFile dataSourceColumnFile2 = dataSourceColumnFileBuilder2.build();

        //column3
        //attach DataSourceValue 3 column3 - value3
        DataSourceColumnFileBuilder dataSourceColumnFileBuilder3 = domainBuilderService.newDataSourceColumnFileBuilderInstance();
        dataSourceColumnFileBuilder3 = (DataSourceColumnFileBuilder) dataSourceColumnFileBuilder3.position(2);
        dataSourceColumnFileBuilder3 = (DataSourceColumnFileBuilder) dataSourceColumnFileBuilder3.name("column3");
        dataSourceColumnFileBuilder3 = dataSourceColumnFileBuilder3.dataSource(fileDataSource);
        dataSourceColumnFileBuilder3 = dataSourceColumnFileBuilder3.dataSourceValue(dataSourceValues3).create();
        DataSourceColumnFile dataSourceColumnFile3 = dataSourceColumnFileBuilder3.build();

        //FileDataSource testDataSource - has columns 1,2,3
        //attach DataSourceColumns to FileDataSource
        Set<DataSourceColumn> columns = new HashSet<>();
        columns.add(dataSourceColumnFile1);
        columns.add(dataSourceColumnFile2);
        columns.add(dataSourceColumnFile3);
        fileDataSource.setColumns(columns);

        fileDataSourceRepository.save(fileDataSource);

    }


    private void fillDataSet(String dataSetName, String dataSourceName) {
        fillDataSource("testDataSource");

        FileDataSource fileDataSource = fileDataSourceRepository.findByName("testDataSource");
        Set<DataSourceColumn> dataSourceColumns = fileDataSource.getColumns();


        //DataSet
        DataSetBuilder dataSetBuilder = domainBuilderService.newDataSetBuilderInstance();
        DataSet dataSet = dataSetBuilder.create().build();

        //DataSetSeries
        DataSetSeriesBuilder dataSetSeriesBuilder = domainBuilderService.newDataSetSeriesBuilderInstance();
        DataSetSeries dataSetSeries = dataSetSeriesBuilder.name("series test").create().build();


        //DataSetSeriesColumn
        DataSetSeriesColumnBuilder dataSetSeriesColumnBuilder = domainBuilderService.newDataSetSeriesColumnBuilderInstance();
        dataSetSeriesColumnBuilder.columnType(ColumnType.X);
        dataSetSeriesColumnBuilder.name("seriesColumnX");

        //todo
        //dataSetSeriesColumnBuilder.dataSourceColumn()
        DataSetSeriesColumn dataSetSeriesColumnX = dataSetSeriesColumnBuilder.build();

        dataSetSeriesColumnBuilder = domainBuilderService.newDataSetSeriesColumnBuilderInstance();
        dataSetSeriesColumnBuilder.columnType(ColumnType.Y);
        dataSetSeriesColumnBuilder.name("seriesColumn1Y");

        DataSetSeriesColumn dataSetSeriesColumn1Y = dataSetSeriesColumnBuilder.build();

        dataSetSeriesColumnBuilder = domainBuilderService.newDataSetSeriesColumnBuilderInstance();
        dataSetSeriesColumnBuilder.columnType(ColumnType.Y);
        dataSetSeriesColumnBuilder.name("seriesColumn2Y");
        DataSetSeriesColumn dataSetSeriesColumn2Y = dataSetSeriesColumnBuilder.build();

        //DataSetSeries
        //attach DataSourceColumn by DataSetSeriesColumn

        //series1 [col1 - col2]
        dataSetSeriesBuilder = domainBuilderService.newDataSetSeriesBuilderInstance();
        List<DataSourceColumn> dataSourceColumnFilesX = new ArrayList<>();
//        dataSourceColumnFilesX.add(dataSourceColumnFile1);
        List<DataSourceColumn> dataSourceColumnFilesY = new ArrayList<>();
//        dataSourceColumnFilesY.add(dataSourceColumnFile2);

        DataSetSeries dataSetSeries1 = dataSetSeriesBuilder.create().name("series1").valueSeriesColumn(null).build();
    }
}
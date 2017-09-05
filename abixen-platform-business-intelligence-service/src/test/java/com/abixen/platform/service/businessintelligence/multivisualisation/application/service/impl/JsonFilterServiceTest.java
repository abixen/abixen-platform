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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.impl;

import com.abixen.platform.service.businessintelligence.infrastructure.configuration.PlatformModuleConfiguration;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.JsonFilterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformModuleConfiguration.class)
public class JsonFilterServiceTest {

    @Autowired
    JsonFilterService jsonFilterService;

    @Mock
    ResultSetMetaData resultSetMetaData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Ignore
    @Test
    public void convertJsonToSqlTest() throws SQLException {
        String jsonCriteria = "{\n" +
                "  \"group\": {\n" +
                "    \"operator\": \"AND\",\n" +
                "    \"rules\": [\n" +
                "      {\n" +
                "        \"condition\": \"=\",\n" +
                "        \"field\": \"REVENUE_GROSS\",\n" +
                "        \"data\": \"23\",\n" +
                "        \"$$hashKey\": \"object:434\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"condition\": \"=\",\n" +
                "        \"field\": \"ID\",\n" +
                "        \"data\": \"4\",\n" +
                "        \"$$hashKey\": \"object:454\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"group\": {\n" +
                "          \"operator\": \"AND\",\n" +
                "          \"rules\": [\n" +
                "            {\n" +
                "              \"condition\": \"=\",\n" +
                "              \"field\": \"REVENUE_NET\",\n" +
                "              \"data\": \"22\",\n" +
                "              \"$$hashKey\": \"object:476\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"condition\": \"=\",\n" +
                "              \"field\": \"SALES_DAY\",\n" +
                "              \"data\": \"2016-10-02T232\",\n" +
                "              \"$$hashKey\": \"object:476\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        \"$$hashKey\": \"object:463\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        when(resultSetMetaData.getColumnCount()).thenReturn(new Integer(3));
        when(resultSetMetaData.getColumnName(0)).thenReturn("REVENUE_GROSS");
        when(resultSetMetaData.getColumnName(1)).thenReturn("ID");
        when(resultSetMetaData.getColumnName(2)).thenReturn("REVENUE_NET");
        when(resultSetMetaData.getColumnName(3)).thenReturn("SALES_DAY");
        when(resultSetMetaData.getColumnTypeName(0)).thenReturn("DOUBLE");
        when(resultSetMetaData.getColumnTypeName(1)).thenReturn("BIGINT");
        when(resultSetMetaData.getColumnTypeName(2)).thenReturn("DOUBLE");
        when(resultSetMetaData.getColumnTypeName(3)).thenReturn("DATE");
        String manualyConvertedCriteria = "(REVENUE_GROSS = 23 AND ID = 4 AND (REVENUE_NET = 22 AND SALES_DAY = '2016-10-02'))";
        String convertedCriteria = jsonFilterService.convertJsonToJpql(jsonCriteria, resultSetMetaData);
        assertEquals(manualyConvertedCriteria, convertedCriteria);
    }


}

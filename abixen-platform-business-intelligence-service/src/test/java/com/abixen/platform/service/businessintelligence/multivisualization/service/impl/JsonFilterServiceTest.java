/*
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 *  This library is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU Lesser General Public License as published by the Free
 *  Software Foundation; either version 2.1 of the License, or (at your option)
 *  any later version.
 *
 *  This library is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 *  details.
 *
 */

package com.abixen.platform.service.businessintelligence.multivisualization.service.impl;

import com.abixen.platform.service.businessintelligence.configuration.PlatformModuleConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformModuleConfiguration.class)
public class JsonFilterServiceTest {

    @Autowired
    JsonFilterServiceImpl jsonFilterService;

    @Test
    public void convertJsonToSqlTest() throws SQLException {
        ResultSetMetaData resultSetMetaData = new ResultSetMetaData() {
            @Override
            public int getColumnCount() throws SQLException {
                return 3;
            }

            @Override
            public boolean isAutoIncrement(int column) throws SQLException {
                return false;
            }

            @Override
            public boolean isCaseSensitive(int column) throws SQLException {
                return false;
            }

            @Override
            public boolean isSearchable(int column) throws SQLException {
                return false;
            }

            @Override
            public boolean isCurrency(int column) throws SQLException {
                return false;
            }

            @Override
            public int isNullable(int column) throws SQLException {
                return 0;
            }

            @Override
            public boolean isSigned(int column) throws SQLException {
                return false;
            }

            @Override
            public int getColumnDisplaySize(int column) throws SQLException {
                return 0;
            }

            @Override
            public String getColumnLabel(int column) throws SQLException {
                return null;
            }

            @Override
            public String getColumnName(int column) throws SQLException {
                switch (column){
                    case 0: return "REVENUE_GROSS";
                    case 1: return "ID";
                    case 2: return "REVENUE_NET";
                    case 3: return "SALES_DAY";
                };
                return null;
            }

            @Override
            public String getSchemaName(int column) throws SQLException {
                return null;
            }

            @Override
            public int getPrecision(int column) throws SQLException {
                return 0;
            }

            @Override
            public int getScale(int column) throws SQLException {
                return 0;
            }

            @Override
            public String getTableName(int column) throws SQLException {
                return null;
            }

            @Override
            public String getCatalogName(int column) throws SQLException {
                return null;
            }

            @Override
            public int getColumnType(int column) throws SQLException {
                return 0;
            }

            @Override
            public String getColumnTypeName(int column) throws SQLException {
                switch (column){
                    case 0: return "DOUBLE";
                    case 1: return "BIGINT";
                    case 2: return "DOUBLE";
                    case 3: return "DATE";
                };
                return null;
            }

            @Override
            public boolean isReadOnly(int column) throws SQLException {
                return false;
            }

            @Override
            public boolean isWritable(int column) throws SQLException {
                return false;
            }

            @Override
            public boolean isDefinitelyWritable(int column) throws SQLException {
                return false;
            }

            @Override
            public String getColumnClassName(int column) throws SQLException {
                return null;
            }

            @Override
            public <T> T unwrap(Class<T> iface) throws SQLException {
                return null;
            }

            @Override
            public boolean isWrapperFor(Class<?> iface) throws SQLException {
                return false;
            }
        };

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
                "              \"data\": \"04-05-2016\",\n" +
                "              \"$$hashKey\": \"object:476\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        \"$$hashKey\": \"object:463\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        String manualyConvertedCriteria = "(REVENUE_GROSS = 23 AND ID = 4 AND (REVENUE_NET = 22 AND SALES_DAY = \"04-05-2016\"))";
        String convertedCriteria = jsonFilterService.convertJsonToJpql(jsonCriteria, resultSetMetaData);
        assertEquals(manualyConvertedCriteria, convertedCriteria);
    }


}

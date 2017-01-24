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
import com.abixen.platform.service.businessintelligence.multivisualization.exception.DatabaseConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformModuleConfiguration.class)
public class JsonFilterServiceTest {

    @Autowired
    JsonFilterServiceImpl jsonFilterService;

    @Test
    public void convertJsonToSqlTest(){
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
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        \"$$hashKey\": \"object:463\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        String manualyConvertedCriteria = "(REVENUE_GROSS = 23 AND ID = 4 AND (REVENUE_NET = 22))";
        String convertedCriteria = jsonFilterService.convertJsonToJpql(jsonCriteria);
        assertEquals(manualyConvertedCriteria, convertedCriteria);
    }


}

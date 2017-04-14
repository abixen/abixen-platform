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

package com.abixen.platform.service.businessintelligence.multivisualisation.service.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DataValueType;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.JsonFilterService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;


@Service
public class JsonFilterServiceImpl implements JsonFilterService {

    private static final int DATA_END_INDEX = 10;
    private static final int DATA_BEGIN_INDEX = 0;

    @Override
    public String convertJsonToJpql(String jsonCriteria, ResultSetMetaData rsmd) throws SQLException {
        Map<String, Object> jsonCriteriaMap = new Gson().fromJson(jsonCriteria, new TypeToken<HashMap<String, Object>>() {
        }.getType());
        String conditionString = "()";
        if (jsonCriteriaMap != null && jsonCriteriaMap.size() > 0) {
            List<Object> queryParameters = new ArrayList<Object>();
            conditionString = convertJsonToJpqlRecursive(jsonCriteriaMap, queryParameters, getColumnTypeMapping(rsmd));
        }
        return !"()".equals(conditionString) ? conditionString : "1=1";
    }

    protected String convertJsonToJpqlRecursive(Map<String, Object> jsonCriteriaMap, List<Object> parameters, Map<String, String> typeMapping) {
        String query = "(";
        int conditionArgumentNumber = 0;
        String currentOperator = "";
        for (String key : jsonCriteriaMap.keySet()) {
            if (key.equals("group")) {
                query = query.substring(0, query.length() - 1);
                query += convertJsonToJpqlRecursive((Map<String, Object>) jsonCriteriaMap.get(key), parameters, typeMapping);
                query = query.substring(0, query.length() - 1);
            } else {
                if (key.equals("operator")) {
                    currentOperator = jsonCriteriaMap.get(key).toString().toUpperCase();
                } else if (key.equals("rules")) {
                    for (Object criteriaObject : (List) jsonCriteriaMap.get(key)) {
                        if (conditionArgumentNumber > 0) {
                            query += currentOperator + " ";
                        }
                        if (criteriaObject instanceof Map) {
                            Map<String, Object> criteriaMap = (Map<String, Object>) criteriaObject;
                            if (criteriaMap.keySet().contains("group")) {
                                query += convertJsonToJpqlRecursive(criteriaMap, parameters, typeMapping);
                            } else {
                                if (criteriaMap.get("field") != null && criteriaMap.get("condition") != null && criteriaMap.get("data") != null) {
                                    if (typeMapping == null) {
                                        query += criteriaMap.get("field").toString() + " " + criteriaMap.get("condition") + " " + criteriaMap.get("data") + " ";
                                    } else {
                                        String fieldTypeName = typeMapping.get(criteriaMap.get("field").toString().toUpperCase());
                                        String data = "";
                                        if (fieldTypeName == null) {
                                            data = criteriaMap.get("data").toString();
                                        } else {
                                            data = prepareDataSection(DataValueType.valueOf(fieldTypeName), criteriaMap.get("data").toString());
                                        }
                                        query += criteriaMap.get("field").toString() + " " + criteriaMap.get("condition") + " " + data + " ";
                                    }
                                }
                            }
                        }
                        conditionArgumentNumber++;
                    }
                    if (query.substring(query.length() - 1, query.length()).equals(" ")) {
                        query = query.substring(0, query.length() - 1);
                    }
                }
            }
        }
        query += ")";
        return query;
    }

    private String prepareDataSection(DataValueType dataValueType, String data) {
        switch (dataValueType) {
            case DOUBLE:
                return data;
            case DATE:
                return "'" + LocalDate.parse(data.substring(DATA_BEGIN_INDEX, DATA_END_INDEX)) + "'";
            case INTEGER:
                return data;
            case STRING:
                return "'" + data + "'";
            default:
                throw new NotImplementedException("Not recognized column type.");
        }
    }

    private Map<String, String> getColumnTypeMapping(ResultSetMetaData rsmd) throws SQLException {
        int columnCount = rsmd.getColumnCount();
        Map<String, String> columnTypeMapping = new HashMap<>();

        IntStream.range(1, columnCount + 1).forEach(i -> {
            try {
                String columnTypeName = rsmd.getColumnTypeName(i);
                if ("BIGINT".equalsIgnoreCase(columnTypeName)) {
                    columnTypeName = "INTEGER";
                }
                if ("VARCHAR".equalsIgnoreCase(columnTypeName)) {
                    columnTypeName = "STRING";
                }
                if ("FLOAT8".equalsIgnoreCase(columnTypeName)) {
                    columnTypeName = "DOUBLE";
                }
                if ("INT8".equalsIgnoreCase(columnTypeName)) {
                    columnTypeName = "INTEGER";
                }
                columnTypeMapping.put(rsmd.getColumnName(i).toUpperCase(), columnTypeName.toUpperCase());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return columnTypeMapping;
    }
}

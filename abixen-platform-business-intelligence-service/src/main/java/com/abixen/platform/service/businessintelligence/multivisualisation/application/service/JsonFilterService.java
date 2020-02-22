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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.DataValueType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


@Service
public class JsonFilterService {

    private static final int DATA_END_INDEX = 10;
    private static final int DATA_BEGIN_INDEX = 0;

    public String convertJsonToJpql(String jsonCriteria, ResultSetMetaData resultSetMetaData) throws SQLException {
        Map<String, Object> jsonCriteriaMap = new Gson().fromJson(jsonCriteria, new TypeToken<HashMap<String, Object>>() {
        }.getType());
        String conditionString = "()";
        if (jsonCriteriaMap != null && jsonCriteriaMap.size() > 0) {
            List<Object> queryParameters = new ArrayList<>();
            conditionString = convertJsonToJpqlRecursive(jsonCriteriaMap, queryParameters, getColumnTypeMapping(resultSetMetaData));
        }
        return !"()".equals(conditionString) ? conditionString : "1=1";
    }

    protected String convertJsonToJpqlRecursive(Map<String, Object> jsonCriteriaMap, List<Object> parameters, Map<String, String> typeMapping) {
        StringBuilder query = new StringBuilder("(");
        int conditionArgumentNumber = 0;
        String currentOperator = "";
        for (String key : jsonCriteriaMap.keySet()) {
            if (key.equals("group")) {
                query = new StringBuilder(query.substring(0, query.length() - 1));
                query.append(convertJsonToJpqlRecursive((Map<String, Object>) jsonCriteriaMap.get(key), parameters, typeMapping));
                query = new StringBuilder(query.substring(0, query.length() - 1));
            } else {
                if (key.equals("operator")) {
                    currentOperator = jsonCriteriaMap.get(key).toString().toUpperCase();
                } else if (key.equals("rules")) {
                    for (Object criteriaObject : (List) jsonCriteriaMap.get(key)) {
                        if (conditionArgumentNumber > 0) {
                            query.append(currentOperator).append(" ");
                        }
                        if (criteriaObject instanceof Map) {
                            Map<String, Object> criteriaMap = (Map<String, Object>) criteriaObject;
                            if (criteriaMap.keySet().contains("group")) {
                                query.append(convertJsonToJpqlRecursive(criteriaMap, parameters, typeMapping));
                            } else {
                                if (criteriaMap.get("field") != null && criteriaMap.get("condition") != null && criteriaMap.get("data") != null) {
                                    if (typeMapping == null) {
                                        query.append(criteriaMap.get("field").toString()).append(" ").append(criteriaMap.get("condition")).append(" ").append(criteriaMap.get("data")).append(" ");
                                    } else {
                                        String fieldTypeName = typeMapping.get(criteriaMap.get("field").toString().toUpperCase());
                                        String data = "";
                                        if (fieldTypeName == null) {
                                            data = criteriaMap.get("data").toString();
                                        } else {
                                            data = prepareDataSection(DataValueType.valueOf(fieldTypeName), criteriaMap.get("data").toString());
                                        }
                                        query.append(criteriaMap.get("field").toString()).append(" ").append(criteriaMap.get("condition")).append(" ").append(data).append(" ");
                                    }
                                }
                            }
                        }
                        conditionArgumentNumber++;
                    }
                    if (query.substring(query.length() - 1, query.length()).equals(" ")) {
                        query = new StringBuilder(query.substring(0, query.length() - 1));
                    }
                }
            }
        }
        query.append(")");

        return query.toString();
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

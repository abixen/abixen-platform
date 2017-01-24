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

package com.abixen.platform.service.businessintelligence.multivisualization.service.impl;

import com.abixen.platform.service.businessintelligence.multivisualization.service.JsonFilterService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class JsonFilterServiceImpl implements JsonFilterService {

    @Override
    public String convertJsonToJpql(String jsonCriteria) {
        Map<String, Object> jsonCriteriaMap = new Gson().fromJson(jsonCriteria, new TypeToken<HashMap<String, Object>>() {
        }.getType());

        List<Object> queryParameters = new ArrayList<Object>();
        return convertJsonToJpqlRecursive(jsonCriteriaMap, queryParameters);
    }

    protected String convertJsonToJpqlRecursive(Map<String, Object> jsonCriteriaMap, List<Object> parameters) {
        String query = "(";
        int conditionArgumentNumber = 0;
        String currentOperator = "";
        for (String key : jsonCriteriaMap.keySet()) {
            if (key.equals("group")) {
                query = query.substring(0, query.length() - 1);
                query += convertJsonToJpqlRecursive((Map<String, Object>) jsonCriteriaMap.get(key), parameters);
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
                                query += convertJsonToJpqlRecursive(criteriaMap, parameters);
                            } else {
                                query += criteriaMap.get("field").toString() + " " + criteriaMap.get("condition") + " " + criteriaMap.get("data") + " ";
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
}

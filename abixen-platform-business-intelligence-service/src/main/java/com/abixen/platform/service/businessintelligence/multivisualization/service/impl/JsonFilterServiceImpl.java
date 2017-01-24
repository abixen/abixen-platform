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
        String query = "( ";
        int conditionArgumentNumber = 0;
        String currentOperator = "";
        for (String key : jsonCriteriaMap.keySet()) {
            if (key.equals("and") || key.equals("or")) {
                currentOperator = key.toUpperCase();
            }
            if (jsonCriteriaMap.get(key) instanceof Map) {
                //TODO - probably unused condition
                query += convertJsonToJpqlRecursive((Map<String, Object>) jsonCriteriaMap.get(key), parameters);
            } else if (jsonCriteriaMap.get(key) instanceof List) {
                for (Object criteriaObject : (List) jsonCriteriaMap.get(key)) {
                    if (conditionArgumentNumber > 0) {
                        query += " " + currentOperator + " ";
                    }
                    if (criteriaObject instanceof Map) {
                        Map<String, Object> criteriaMap = (Map<String, Object>) criteriaObject;
                        if (criteriaMap.keySet().contains("or") || criteriaMap.keySet().contains("and")) {
                            query += convertJsonToJpqlRecursive(criteriaMap, parameters);
                        } //else {
                            //String condition = SqlParameterUtil.getSqlConditionLeftArgument(getDomainClass(), (String) criteriaMap.get("name")) + " " + SqlOperatorUtil.convertJsonToSqlOperator((String) criteriaMap.get("operation"));
                            //query += condition + " :p" + parameters.size();
                            //parameters.add(SqlParameterUtil.getParameterValue(getDomainClass(), (String) criteriaMap.get("name"), criteriaMap.get("value")));
                        //}
                    }
                    conditionArgumentNumber++;
                }
            }
            conditionArgumentNumber++;
            //TODO - probably unused condition
        }
        query += " )";
        return query;
    }


}

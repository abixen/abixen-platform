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

package com.abixen.platform.core.repository.custom.impl;

import com.abixen.platform.core.repository.custom.PlatformJpaRepository;
import com.abixen.platform.core.util.SqlOperatorUtil;
import com.abixen.platform.core.util.SqlParameterUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.*;


public class PlatformJpaRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements PlatformJpaRepository<T, ID> {

    static Logger log = Logger.getLogger(PlatformJpaRepositoryImpl.class.getName());

    private final EntityManager entityManager;

    public PlatformJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public Page<T> findAllByJsonCriteria(String jsonCriteria, Pageable pageable) {
        //TODO
        log.debug("findAllByJsonCriteria() - jsonCriteria: " + jsonCriteria + ", pageable: " + pageable);
        Map<String, Object> jsonCriteriaMap = new Gson().fromJson(jsonCriteria, new TypeToken<HashMap<String, Object>>() {
        }.getType());

        StringBuilder queryStringBuilder = new StringBuilder();
        queryStringBuilder.append("FROM ");
        queryStringBuilder.append(getDomainClass().getCanonicalName());
        queryStringBuilder.append(" e WHERE ");

        List<Object> queryParameters = new ArrayList<Object>();
        queryStringBuilder.append(convertJsonStringJPQL(jsonCriteriaMap, queryParameters));

        String queryString = queryStringBuilder.toString();

        log.debug("queryString: " + queryString);
        log.debug("queryParameters: " + queryParameters);

        String resultListQueryString = "SELECT e " + queryString;
        String countQueryString = "SELECT COUNT(e) " + queryString;

        Query resultListQuery = entityManager.createQuery(resultListQueryString);
        Query countQuery = entityManager.createQuery(countQueryString);

        for (int i = 0; i < queryParameters.size(); i++) {
            resultListQuery.setParameter("p" + i, queryParameters.get(i));
            countQuery.setParameter("p" + i, queryParameters.get(i));
        }

        return (Page) (pageable == null ? new PageImpl(resultListQuery.getResultList()) : this.readPage(resultListQuery, countQuery, pageable));
    }

    protected Page<T> readPage(Query resultListQuery, Query countQuery, Pageable pageable) {
        resultListQuery.setFirstResult(pageable.getOffset());
        resultListQuery.setMaxResults(pageable.getPageSize());
        Long total = (Long) countQuery.getSingleResult();
        List content = total.longValue() > (long) pageable.getOffset() ? resultListQuery.getResultList() : Collections.emptyList();
        return new PageImpl(content, pageable, total.longValue());
    }

    protected String convertJsonStringJPQL(Map<String, Object> jsonCriteriaMap, List<Object> parameters) {
        String query = "( ";
        int conditionArgumentNumber = 0;
        String currentOperator = "";
        for (String key : jsonCriteriaMap.keySet()) {
            if (key.equals("and") || key.equals("or")) {
                currentOperator = key.toUpperCase();
            }
            if (jsonCriteriaMap.get(key) instanceof Map) {//TODO - probably unused condition
                query += convertJsonStringJPQL((Map<String, Object>) jsonCriteriaMap.get(key), parameters);
            } else if (jsonCriteriaMap.get(key) instanceof List) {
                for (Object criteriaObject : (List) jsonCriteriaMap.get(key)) {
                    if (conditionArgumentNumber > 0) {
                        query += " " + currentOperator + " ";
                    }
                    if (criteriaObject instanceof Map) {
                        Map<String, Object> criteriaMap = (Map<String, Object>) criteriaObject;
                        if (criteriaMap.keySet().contains("or") || criteriaMap.keySet().contains("and")) {
                            query += convertJsonStringJPQL(criteriaMap, parameters);
                        } else {
                            String condition = SqlParameterUtil.getSqlConditionLeftArgument(getDomainClass(), (String) criteriaMap.get("name")) + " " + SqlOperatorUtil.convertJsonToSqlOperator((String) criteriaMap.get("operation"));
                            query += condition + " :p" + parameters.size();
                            parameters.add(SqlParameterUtil.getParameterValue(getDomainClass(), (String) criteriaMap.get("name"), criteriaMap.get("value")));
                        }
                    }
                    conditionArgumentNumber++;
                }
            }
            conditionArgumentNumber++;//TODO - probably unused condition
        }
        query += " )";
        return query;
    }

}

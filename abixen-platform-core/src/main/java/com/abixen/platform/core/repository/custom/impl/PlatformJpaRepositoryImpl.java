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

import com.abixen.platform.core.model.enumtype.AclClassName;
import com.abixen.platform.core.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.repository.custom.PlatformJpaRepository;
import com.abixen.platform.core.security.PlatformUser;
import com.abixen.platform.core.util.SqlOperatorUtil;
import com.abixen.platform.core.util.SqlParameterUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class PlatformJpaRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements PlatformJpaRepository<T, ID> {

    private final EntityManager entityManager;

    public PlatformJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public List<T> findAllSecured(String queryString, String filteredObjectAlias, AclClassName aclClassName, PermissionName permissionName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.error("An user is not signed in.");
            return new ArrayList<>();
        }

        PlatformUser platformUser = (PlatformUser) authentication.getPrincipal();
        User user = findUser(platformUser.getId());
        String aclFilterQueryString = getAclFilterQuery(filteredObjectAlias, user, aclClassName, permissionName);

        String resultQueryString = queryString.replace("#{securityFilter}", aclFilterQueryString);
        log.debug("resultQueryString={}", resultQueryString);

        return entityManager.createQuery(resultQueryString).getResultList();
    }

    private String getAclFilterQuery(String filteredObjectAlias, User user, AclClassName aclClassName, PermissionName permissionName) {

        StringBuilder aclFilterQueryBuilder = new StringBuilder();

        aclFilterQueryBuilder.append(" ( 0 < (SELECT COUNT(u) FROM User u JOIN u.roles r JOIN r.permissions p WHERE ")
                .append("u.id = ")
                .append(user.getId())
                .append("AND p.permissionName = '")
                .append(permissionName)
                .append("')) ")
                .append("OR (0 < (SELECT COUNT(ae) FROM AclEntry ae WHERE ")
                .append("ae.permission.permissionName = '")
                .append(permissionName)
                .append("' AND ")
                .append("ae.aclSid.sidType = 'ROLE' AND ")
                .append("ae.aclSid.sidId IN (")
                .append(String.join(",", user.getRoles().stream().map(r -> r.getId().toString()).collect(Collectors.toList())))
                .append(") AND ")
                .append("ae.aclObjectIdentity.aclClass.aclClassName = '")
                .append(aclClassName)
                .append("' AND ")
                .append("ae.aclObjectIdentity.objectId = ")
                .append(filteredObjectAlias)
                .append(".id)) ")
                .append("OR (0 < (SELECT COUNT(ae) FROM AclEntry ae WHERE ")
                .append("ae.permission.permissionName = '")
                .append(permissionName)
                .append("' AND ")
                .append("ae.aclSid.sidType = 'OWNER' AND ")
                .append("ae.aclSid.sidId IN (0) AND ")
                .append("ae.aclObjectIdentity.aclClass.aclClassName = '")
                .append(aclClassName)
                .append("' AND ")
                .append("ae.aclObjectIdentity.objectId = ")
                .append(filteredObjectAlias)
                .append(".id))");

        String aclFilterQuery = aclFilterQueryBuilder.toString();

        return aclFilterQuery;
    }

    private User findUser(Long userId) {
        String queryString = "FROM User u WHERE u.id = :userId";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("userId", userId);
        return (User) query.getSingleResult();
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
            if (jsonCriteriaMap.get(key) instanceof Map) {
                //TODO - probably unused condition
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
            conditionArgumentNumber++;
            //TODO - probably unused condition
        }
        query += " )";
        return query;
    }
}

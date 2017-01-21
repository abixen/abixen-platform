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


import com.abixen.platform.core.exception.PlatformCoreException;
import com.abixen.platform.core.form.search.SearchField;
import com.abixen.platform.core.form.search.SearchForm;
import com.abixen.platform.core.model.impl.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SearchFormSpecifications {
    public static Specification<User> bySearchForm(final SearchForm searchForm) {
        return (candidateRoot, criteriaQuery, criteriaBuilder) -> {
            log.debug("findAll() - searchFormClass: {}, searchForm: {}", searchForm.getClass(), searchForm);

            List<Predicate> predicates = new ArrayList<>();

            List<Field> fields = Arrays
                    .stream(searchForm.getClass().getDeclaredFields())
                    .filter(field -> field.getAnnotationsByType(SearchField.class).length == 1)
                    .collect(Collectors.toList());

            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    log.debug("field - name: {}, type: {}, value: {}", field.getName(), field.getType(), field.get(searchForm));

                    SearchField searchField = field.getAnnotation(SearchField.class);

                    String domainField = searchField.domainField();
                    if ("".equals(domainField)) {
                        domainField = field.getName();
                    }
                    SearchField.Operator operator = searchField.operator();

                    if (field.get(searchForm) != null) {
                        if (SearchField.Operator.EQUALS.equals(operator)) {
                            predicates.add(criteriaBuilder.equal(candidateRoot.get(domainField), field.get(searchForm)));
                        } else if (SearchField.Operator.LIKE.equals(operator)) {
                            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(candidateRoot.get(domainField)),
                                    "%" + field.get(searchForm).toString().toLowerCase() + "%"));

                        }
                    }
                }

            } catch (IllegalAccessException e) {
                throw new PlatformCoreException(e);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
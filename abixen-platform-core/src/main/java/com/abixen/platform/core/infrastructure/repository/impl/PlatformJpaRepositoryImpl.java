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

package com.abixen.platform.core.infrastructure.repository.impl;

import com.abixen.platform.common.domain.model.search.SearchForm;
import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.infrastructure.repository.custom.impl.specification.AndSpecifications;
import com.abixen.platform.core.infrastructure.repository.custom.impl.specification.SearchFormSpecifications;
import com.abixen.platform.core.infrastructure.repository.custom.impl.specification.SecuredSpecifications;
import com.abixen.platform.core.infrastructure.repository.PlatformJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class PlatformJpaRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements PlatformJpaRepository<T, ID> {

    //FIXME - unused?
    private final EntityManager entityManager;

    public PlatformJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public Page<T> findAll(Pageable pageable, SearchForm searchForm, User user, AclClassName aclClassName, PermissionName permissionName) {
        Specification<T> securedSpecification = SecuredSpecifications.getSpecification(user, aclClassName, permissionName);
        Specification<T> searchFormSpecification = SearchFormSpecifications.getSpecification(searchForm);
        Specification<T> specification = AndSpecifications.getSpecification(searchFormSpecification, securedSpecification);

        return (Page) (null == pageable ? new PageImpl(this.findAll()) : this.findAll(Specifications.where(specification), pageable));
    }

    public List<T> findAll(SearchForm searchForm, User user, AclClassName aclClassName, PermissionName permissionName) {
        Specification<T> securedSpecification = SecuredSpecifications.getSpecification(user, aclClassName, permissionName);
        Specification<T> searchFormSpecification = SearchFormSpecifications.getSpecification(searchForm);
        Specification<T> specification = AndSpecifications.getSpecification(searchFormSpecification, securedSpecification);

        return this.findAll(Specifications.where(specification));
    }

    public Page<T> findAll(Pageable pageable, User user, AclClassName aclClassName, PermissionName permissionName) {
        Specification<T> securedSpecification = SecuredSpecifications.getSpecification(user, aclClassName, permissionName);

        return (Page) (null == pageable ? new PageImpl(this.findAll()) : this.findAll(Specifications.where(securedSpecification), pageable));
    }

    public List<T> findAll(User user, AclClassName aclClassName, PermissionName permissionName) {
        Specification<T> securedSpecification = SecuredSpecifications.getSpecification(user, aclClassName, permissionName);

        return this.findAll(Specifications.where(securedSpecification));
    }

    public Page<T> findAll(Pageable pageable, SearchForm searchForm) {
        Specification<T> searchFormSpecification = SearchFormSpecifications.getSpecification(searchForm);

        return (Page) (null == pageable ? new PageImpl(this.findAll()) : this.findAll(Specifications.where(searchFormSpecification), pageable));
    }

    public List<T> findAll(SearchForm searchForm) {
        Specification<T> searchFormSpecification = SearchFormSpecifications.getSpecification(searchForm);

        return this.findAll(Specifications.where(searchFormSpecification));
    }
}
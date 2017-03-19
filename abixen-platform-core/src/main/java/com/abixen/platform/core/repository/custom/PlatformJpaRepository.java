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

package com.abixen.platform.core.repository.custom;

import com.abixen.platform.common.form.search.SearchForm;
import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;


@NoRepositoryBean
public interface PlatformJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    List<T> findAll(SearchForm searchForm);

    Page<T> findAll(Pageable pageable, SearchForm searchForm);

    List<T> findAll(SearchForm searchForm, User user, AclClassName aclClassName, PermissionName permissionName);

    Page<T> findAll(Pageable pageable, SearchForm searchForm, User user, AclClassName aclClassName, PermissionName permissionName);

    List<T> findAll(User user, AclClassName aclClassName, PermissionName permissionName);

    Page<T> findAll(Pageable pageable, User user, AclClassName aclClassName, PermissionName permissionName);
}
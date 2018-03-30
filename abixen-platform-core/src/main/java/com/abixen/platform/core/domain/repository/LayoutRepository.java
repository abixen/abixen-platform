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

package com.abixen.platform.core.domain.repository;

import com.abixen.platform.core.application.form.LayoutSearchForm;
import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.core.domain.model.Layout;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.infrastructure.repository.PlatformJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface LayoutRepository extends PlatformJpaRepository<Layout, Long>, JpaSpecificationExecutor<Layout> {

    default Page<Layout> findAllSecured(Pageable pageable, LayoutSearchForm layoutSearchForm, User user, PermissionName permissionName) {
        return findAll(pageable, layoutSearchForm, user, AclClassName.LAYOUT, permissionName);
    }

    default List<Layout> findAllSecured(User user, PermissionName permissionName) {
        return findAll(user, AclClassName.LAYOUT, permissionName);
    }
}
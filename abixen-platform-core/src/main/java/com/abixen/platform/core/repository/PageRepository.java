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

package com.abixen.platform.core.repository;

import com.abixen.platform.core.form.PageSearchForm;
import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.repository.custom.PlatformJpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PageRepository extends PlatformJpaRepository<Page, Long> {

    default org.springframework.data.domain.Page<Page> findAllSecured(Pageable pageable, PageSearchForm pageSearchForm, User user, PermissionName permissionName) {
        return findAll(pageable, pageSearchForm, user, AclClassName.PAGE, permissionName);
    }

    default List<Page> findAllSecured(User user, PermissionName permissionName) {
        return findAll(user, AclClassName.PAGE, permissionName);
    }
}
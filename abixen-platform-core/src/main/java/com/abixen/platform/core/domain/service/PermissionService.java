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

package com.abixen.platform.core.domain.service;

import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.core.application.form.PermissionSearchForm;
import com.abixen.platform.core.domain.model.Permission;
import com.abixen.platform.core.domain.model.PermissionAclClassCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PermissionService {

    Permission find(Long id);

    Permission find(PermissionName permissionName);

    List<Permission> findAll();

    List<Permission> findAll(final PermissionAclClassCategory permissionAclClassCategory);

    Page<Permission> findAll(Pageable pageable, PermissionSearchForm permissionSearchForm);

}
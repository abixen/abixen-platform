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

import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.ModuleType;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.repository.custom.PlatformJpaRepository;

import java.util.List;


public interface ModuleTypeRepository extends PlatformJpaRepository<ModuleType, Long> {

    ModuleType findByName(String name);

    default List<ModuleType> findAllSecured(User user, PermissionName permissionName) {
        return findAll(user, AclClassName.MODULE_TYPE, permissionName);
    }
}
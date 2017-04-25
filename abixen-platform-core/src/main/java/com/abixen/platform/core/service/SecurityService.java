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

package com.abixen.platform.core.service;

import com.abixen.platform.common.model.SecurableModel;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.common.security.PlatformUser;


public interface SecurityService {

    Boolean hasUserPermissionToObject(User user, PermissionName permissionName, SecurableModel securableModel);

    Boolean hasUserPermissionToClass(User user, PermissionName permissionName, String domainClassName);

    Boolean hasUserRole(User user, Role role);

    PlatformUser getAuthorizedUser();

    boolean hasPermission(String username, SecurableModel securibleObject, String permissionName);
}
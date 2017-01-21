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

import com.abixen.platform.core.form.RoleForm;
import com.abixen.platform.core.form.RolePermissionsForm;
import com.abixen.platform.core.form.RoleSearchForm;
import com.abixen.platform.core.model.impl.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface RoleService {

    Role createRole(Role role);

    Role updateRole(Role role);

    void deleteRole(Long id);

    Page<Role> findAllRoles(Pageable pageable, RoleSearchForm roleSearchForm);

    Role findRole(Long id);

    Role buildRole(RoleForm roleForm);

    Role buildRolePermissions(RolePermissionsForm rolePermissionsForm);

    List<Role> findAllRoles();

}

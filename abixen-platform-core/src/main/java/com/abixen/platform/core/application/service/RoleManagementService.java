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

package com.abixen.platform.core.application.service;

import com.abixen.platform.core.application.dto.RoleDto;
import com.abixen.platform.core.application.form.RoleForm;
import com.abixen.platform.core.application.form.RolePermissionsForm;
import com.abixen.platform.core.application.form.RoleSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RoleManagementService {

    RoleDto findRole(Long id);

    Page<RoleDto> findAllRoles(Pageable pageable, RoleSearchForm roleSearchForm);

    RoleForm createRole(RoleForm roleForm);

    RoleForm updateRole(RoleForm roleForm);

    void deleteRole(Long id);

    RolePermissionsForm findRolePermissions(Long id);

    RolePermissionsForm updateRolePermissions(RolePermissionsForm rolePermissionsForm);

}
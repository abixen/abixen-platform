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

package com.abixen.platform.core.application.service

import com.abixen.platform.common.model.enumtype.RoleType
import com.abixen.platform.core.application.converter.PermissionToPermissionDtoConverter
import com.abixen.platform.core.application.converter.RoleToRoleDtoConverter
import com.abixen.platform.core.application.dto.RoleDto
import com.abixen.platform.core.application.form.RoleSearchForm
import com.abixen.platform.core.application.service.impl.RoleManagementServiceImpl
import com.abixen.platform.core.domain.model.Role
import com.abixen.platform.core.domain.model.RoleBuilder
import com.abixen.platform.core.domain.service.PermissionService
import com.abixen.platform.core.domain.service.RoleService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification


class RoleManagementServiceTest extends Specification {

    private RoleService roleService;
    private PermissionService permissionService
    private RoleToRoleDtoConverter roleToRoleDtoConverter
    private PermissionToPermissionDtoConverter permissionToPermissionDtoConverter
    private RoleManagementService roleManagementService


    void setup() {
        roleService = Mock()
        permissionService = Mock()
        roleToRoleDtoConverter = Mock()
        permissionToPermissionDtoConverter = Mock()
        roleManagementService = new RoleManagementServiceImpl(roleService,
                permissionService,
                roleToRoleDtoConverter,
                permissionToPermissionDtoConverter)
    }


    void "should find role"() {
        given:
        final Role role = new RoleBuilder()
                .name("name")
                .type(RoleType.ROLE_USER)
                .build()

        final RoleDto roleDto = new RoleDto()

        roleService.find(1L) >> role
        roleToRoleDtoConverter.convert(role) >> roleDto

        when:
        final RoleDto foundRoleDto = roleManagementService.findRole(1L)

        then:
        foundRoleDto != null
        foundRoleDto == roleDto
        1 * roleService.find(1L) >> role
        1 * roleToRoleDtoConverter.convert(role) >> roleDto
        0 * _
    }

    void "should find all roles"() {
        given:
        final Role role = new RoleBuilder()
                .name("name")
                .type(RoleType.ROLE_USER)
                .build()

        final RoleDto roleDto = new RoleDto()
        final Pageable pageable = new PageRequest(0, 1);
        final RoleSearchForm roleSearchForm = new RoleSearchForm()
        final Page<Role> roles = new PageImpl<Role>([role])
        final Page<RoleDto> roleDtos = new PageImpl<RoleDto>([roleDto])

        roleService.findAll(pageable, roleSearchForm) >> roles
        roleToRoleDtoConverter.convertToPage(roles) >> roleDtos

        when:
        final Page<RoleDto> foundRoleDtos = roleManagementService.findAllRoles(pageable, roleSearchForm)

        then:
        foundRoleDtos != null
        foundRoleDtos == roleDtos
        1 * roleService.findAll(pageable, roleSearchForm) >> roles
        1 * roleToRoleDtoConverter.convertToPage(roles) >> roleDtos
        0 * _
    }

}
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

package com.abixen.platform.core.application.service.impl;

import com.abixen.platform.core.application.dto.PermissionDto;
import com.abixen.platform.core.application.dto.RoleDto;
import com.abixen.platform.core.application.dto.RolePermissionDto;
import com.abixen.platform.core.application.form.RoleForm;
import com.abixen.platform.core.application.form.RolePermissionsForm;
import com.abixen.platform.core.application.form.RoleSearchForm;
import com.abixen.platform.core.application.service.RoleManagementService;
import com.abixen.platform.core.domain.model.Permission;
import com.abixen.platform.core.domain.model.Role;
import com.abixen.platform.core.domain.model.RoleBuilder;
import com.abixen.platform.core.application.service.PermissionService;
import com.abixen.platform.core.domain.service.RoleService;
import com.abixen.platform.core.interfaces.web.facade.converter.PermissionToPermissionDtoConverter;
import com.abixen.platform.core.interfaces.web.facade.converter.RoleToRoleDtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Transactional
@Service
public class RoleManagementServiceImpl implements RoleManagementService {

    private final RoleService roleService;
    private final PermissionService permissionService;
    private final RoleToRoleDtoConverter roleToRoleDtoConverter;
    private final PermissionToPermissionDtoConverter permissionToPermissionDtoConverter;

    @Autowired
    public RoleManagementServiceImpl(RoleService roleService,
                                     PermissionService permissionService,
                                     RoleToRoleDtoConverter roleToRoleDtoConverter,
                                     PermissionToPermissionDtoConverter permissionToPermissionDtoConverter) {
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.roleToRoleDtoConverter = roleToRoleDtoConverter;
        this.permissionToPermissionDtoConverter = permissionToPermissionDtoConverter;
    }


    @Override
    public RoleDto findRole(final Long id) {
        log.debug("findRole() - id: {}", id);

        final Role role = roleService.find(id);

        return roleToRoleDtoConverter.convert(role);
    }

    @Override
    public Page<RoleDto> findAllRoles(final Pageable pageable, final RoleSearchForm roleSearchForm) {
        log.debug("findAllRoles() - roleSearchForm: {}", roleSearchForm);

        final Page<Role> roles = roleService.findAll(pageable, roleSearchForm);

        return roleToRoleDtoConverter.convertToPage(roles);
    }

    @Override
    public RoleForm createRole(final RoleForm roleForm) {
        log.debug("createRole() - roleForm: {}", roleForm);

        final Role role = new RoleBuilder()
                .name(roleForm.getName())
                .type(roleForm.getRoleType())
                .build();

        final Role createdRole = roleService.create(role);

        return new RoleForm(createdRole);
    }

    @Override
    public RoleForm updateRole(final RoleForm roleForm) {
        log.debug("updateRole() - roleForm: {}", roleForm);

        final Role role = roleService.find(roleForm.getId());
        role.changeDetails(role.getName(), roleForm.getRoleType());
        final Role updatedRole = roleService.update(role);

        return new RoleForm(updatedRole);
    }

    @Override
    public void deleteRole(final Long id) {
        log.debug("deleteRole() - id: {}", id);

        roleService.delete(id);
    }

    @Override
    public RolePermissionsForm findRolePermissions(final Long id) {
        log.debug("findRolePermissions() - id: {}", id);

        final Role role = roleService.find(id);
        final List<Permission> allPermissions = permissionService.findAllPermissions();

        final RoleDto roleDto = roleToRoleDtoConverter.convert(role);
        final List<PermissionDto> allPermissionDtos = permissionToPermissionDtoConverter.convertToList(allPermissions);

        return new RolePermissionsForm(roleDto, allPermissionDtos);
    }

    @Override
    public RolePermissionsForm updateRolePermissions(final RolePermissionsForm rolePermissionsForm) {
        log.debug("updateRolePermissions() - rolePermissionsForm: {}", rolePermissionsForm);

        final Set<Permission> newPermissions = new HashSet<>();

        for (RolePermissionDto rolePermissionDto : rolePermissionsForm.getRolePermissions()) {
            if (rolePermissionDto.isSelected()) {
                newPermissions.add(permissionService.findPermission(rolePermissionDto.getPermission().getId()));
            }
        }

        final Role role = roleService.find(rolePermissionsForm.getRole().getId());
        role.changePermissions(newPermissions);

        final Role updatedRole = roleService.update(role);
        final RoleDto updatedRoleDto = roleToRoleDtoConverter.convert(updatedRole);

        final List<Permission> allPermissions = permissionService.findAllPermissions();
        final List<PermissionDto> allPermissionDtos = permissionToPermissionDtoConverter.convertToList(allPermissions);

        return new RolePermissionsForm(updatedRoleDto, allPermissionDtos);
    }

}
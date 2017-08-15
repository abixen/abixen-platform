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

import com.abixen.platform.common.model.enumtype.AclSidType;
import com.abixen.platform.core.application.dto.RolePermissionDto;
import com.abixen.platform.core.application.form.RoleForm;
import com.abixen.platform.core.application.form.RolePermissionsForm;
import com.abixen.platform.core.application.form.RoleSearchForm;
import com.abixen.platform.core.application.service.AclSidService;
import com.abixen.platform.core.application.service.PermissionService;
import com.abixen.platform.core.application.service.RoleService;
import com.abixen.platform.core.domain.model.Role;
import com.abixen.platform.core.domain.model.RoleBuilder;
import com.abixen.platform.core.domain.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionService permissionService;
    private final AclSidService aclSidService;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,
                           PermissionService permissionService,
                           AclSidService aclSidService) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
        this.aclSidService = aclSidService;
    }

    @Override
    public Role createRole(Role role) {
        log.debug("createRole() - role: " + role);
        Role createdRole = roleRepository.save(role);
        aclSidService.createAclSid(AclSidType.ROLE, createdRole.getId());
        return createdRole;
    }

    @Override
    public Role updateRole(Role role) {
        log.debug("updateRole() - role: " + role);
        return roleRepository.save(role);
    }

    @Override
    public RoleForm updateRole(RoleForm roleForm) {
        log.debug("updateRole() - roleForm: {}", roleForm);
        Role role = roleRepository.findOne(roleForm.getId());
        role.changeDetails(roleForm.getName(), roleForm.getRoleType());
        return new RoleForm(roleRepository.save(role));
    }

    @Override
    public void deleteRole(Long id) {
        log.debug("deleteRole() - id: " + id);
        roleRepository.delete(id);
    }

    @Override
    public Page<Role> findAllRoles(Pageable pageable, RoleSearchForm roleSearchForm) {
        log.debug("findAllRoles() - pageable: " + pageable);
        return roleRepository.findAll(pageable, roleSearchForm);
    }

    @Override
    public Role findRole(Long id) {
        log.debug("findRole() - id: " + id);
        return roleRepository.findOne(id);
    }

    @Override
    public Role buildRole(RoleForm roleForm) {
        log.debug("buildRole() - roleForm: " + roleForm);

        RoleBuilder roleBuilder = new RoleBuilder();
        roleBuilder.name(roleForm.getName());
        roleBuilder.type(roleForm.getRoleType());
        return roleBuilder.build();
    }

    @Override
    public Role buildRolePermissions(RolePermissionsForm rolePermissionsForm) {
        log.debug("buildRolePermissions() - rolePermissionsForm: " + rolePermissionsForm);

        Role role = findRole(rolePermissionsForm.getRole().getId());
        role.getPermissions().clear();

        for (RolePermissionDto rolePermissionDto : rolePermissionsForm.getRolePermissions()) {
            if (rolePermissionDto.isSelected()) {
                role.getPermissions().add(permissionService.findPermission(rolePermissionDto.getPermission().getId()));
            }
        }
        return role;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

}
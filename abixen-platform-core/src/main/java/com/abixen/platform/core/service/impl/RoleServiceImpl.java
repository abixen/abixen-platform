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

package com.abixen.platform.core.service.impl;

import com.abixen.platform.core.dto.RolePermissionDto;
import com.abixen.platform.core.form.RoleForm;
import com.abixen.platform.core.form.RolePermissionsForm;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.core.repository.RoleRepository;
import com.abixen.platform.core.service.DomainBuilderService;
import com.abixen.platform.core.service.PermissionService;
import com.abixen.platform.core.service.RoleService;
import com.abixen.platform.core.util.RoleBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    private static Logger log = Logger.getLogger(RoleServiceImpl.class.getName());

    @Resource
    private RoleRepository roleRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private DomainBuilderService domainBuilderService;

    @Override
    public Role createRole(Role role) {
        log.debug("createRole() - role: " + role);
        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        log.debug("updateRole() - role: " + role);
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        log.debug("deleteRole() - id: " + id);
        roleRepository.delete(id);
    }

    @Override
    public Page<Role> findAllRoles(Pageable pageable) {
        log.debug("findAllRoles() - pageable: " + pageable);
        return roleRepository.findAll(pageable);
    }

    @Override
    public Role findRole(Long id) {
        log.debug("findRole() - id: " + id);
        return roleRepository.findOne(id);
    }

    @Override
    public Role buildRole(RoleForm roleForm) {
        log.debug("buildRole() - roleForm: " + roleForm);

        RoleBuilder roleBuilder = domainBuilderService.newRoleBuilderInstance();
        roleBuilder.name(roleForm.getName());
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

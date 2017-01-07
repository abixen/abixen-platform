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

package com.abixen.platform.core.controller;

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.form.RoleForm;
import com.abixen.platform.core.form.RolePermissionsForm;
import com.abixen.platform.core.model.enumtype.RoleType;
import com.abixen.platform.core.model.impl.Permission;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.core.service.PermissionService;
import com.abixen.platform.core.service.RoleService;
import com.abixen.platform.core.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/admin/roles")
public class RoleController {


    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Role> getRoles(@PageableDefault(size = 1, page = 0) Pageable pageable) {
        log.debug("getRoles()");

        Page<Role> roles = roleService.findAllRoles(pageable);
        for (Role role : roles) {
            log.debug("role: " + role);
        }

        return roles;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Role getRole(@PathVariable Long id) {
        log.debug("getRole() - id: " + id);

        Role role = roleService.findRole(id);

        return role;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createRole(@RequestBody @Valid RoleForm roleForm, BindingResult bindingResult) {
        log.debug("save() - roleForm: " + roleForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(roleForm, formErrors);
        }

        Role role = roleService.buildRole(roleForm);
        //TODO
        role.setRoleType(RoleType.ROLE_USER);
        roleService.createRole(role);

        return new FormValidationResultDto(roleForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Role updateRole(@PathVariable("id") Long id, @RequestBody Role role) {
        log.debug("update() - id: " + id + ", role: " + role);
        return roleService.updateRole(role);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteRole(@PathVariable("id") long id) {
        log.debug("delete() - id: " + id);
        roleService.deleteRole(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.GET)
    public RolePermissionsForm getRolePermissions(@PathVariable Long id) {
        log.debug("getRolePermissionsForm() - id: " + id);

        Role role = roleService.findRole(id);
        List<Permission> allPermissions = permissionService.findAllPermissions();

        RolePermissionsForm rolePermissionsForm = new RolePermissionsForm(role, allPermissions);

        return rolePermissionsForm;
    }

    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.PUT)
    public FormValidationResultDto updateRolePermissions(@PathVariable("id") Long id, @RequestBody @Valid RolePermissionsForm rolePermissionsForm, BindingResult bindingResult) {
        log.debug("updateRolePermissions() - id: " + id + ", rolePermissionsForm: " + rolePermissionsForm);

        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(rolePermissionsForm, formErrors);
        }

        Role role = roleService.buildRolePermissions(rolePermissionsForm);
        roleService.updateRole(role);

        return new FormValidationResultDto(rolePermissionsForm);
    }
}

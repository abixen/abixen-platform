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

import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.AclSidType;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.common.infrastructure.security.PlatformUser;
import com.abixen.platform.core.domain.model.AclEntry;
import com.abixen.platform.core.domain.model.Permission;
import com.abixen.platform.core.domain.model.Role;
import com.abixen.platform.core.domain.model.SecurableModel;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.service.AclEntryService;
import com.abixen.platform.core.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@PlatformApplicationService
public class SecurityService {

    private final UserService userService;
    private final AclEntryService aclEntryService;

    @Autowired
    public SecurityService(UserService userService,
                           AclEntryService aclEntryService) {
        this.userService = userService;
        this.aclEntryService = aclEntryService;

    }

    public Boolean hasUserPermissionToObject(final User user, final PermissionName permissionName, final SecurableModel securableModel) {
        if (user == null) {
            throw new IllegalArgumentException("User can not be null.");
        }
        if (permissionName == null) {
            throw new IllegalArgumentException("Permission Name can not be null.");
        }
        if (securableModel == null) {
            throw new IllegalArgumentException("SecurableModel can not be null.");
        }

        if (hasUserPermissionToClass(user, permissionName, securableModel.getClass().getCanonicalName())) {
            return true;
        }

        final List<Long> userRoleIds = new ArrayList<>();
        for (Role role : user.getRoles()) {
            userRoleIds.add(role.getId());
        }
        final List<AclEntry> rolesAclEntries = aclEntryService.findAll(permissionName, AclSidType.ROLE, userRoleIds, AclClassName.getByName(securableModel.getClass().getCanonicalName()), securableModel.getId());

        if (rolesAclEntries.size() > 0) {
            if (log.isDebugEnabled()) {
                log.debug("User " + user.getUsername() + " has permission " + permissionName + " to object " + securableModel.getClass().getCanonicalName() + "[id=" + securableModel.getId() + "] based on the ACL security settings.");
            }
            return true;
        }

        if (securableModel.getCreatedBy() != null && securableModel.getCreatedBy().getId().equals(user.getId())) {
            List<AclEntry> ownerAclEntries = aclEntryService.findAll(permissionName, AclSidType.OWNER, 0L, AclClassName.getByName(securableModel.getClass().getCanonicalName()), securableModel.getId());
            if (ownerAclEntries.size() > 0) {
                if (log.isDebugEnabled()) {
                    log.debug("User " + user.getUsername() + " has permission " + permissionName + " to object " + securableModel.getClass().getCanonicalName() + "[id=" + securableModel.getId() + "] based on that he is the owner.");
                }
                return true;
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("User " + user.getUsername() + " has not permission " + permissionName + " to object " + securableModel.getClass().getCanonicalName() + "[id=" + securableModel.getId() + "].");
        }
        return false;
    }

    public Boolean hasUserPermissionToClass(User user, PermissionName permissionName, String domainCanonicalClassName) {
        if (user == null) {
            throw new IllegalArgumentException("User can not be null.");
        }
        if (permissionName == null) {
            throw new IllegalArgumentException("Permission Name can not be null.");
        }

        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                if (permission.getPermissionName().equals(permissionName)) {
                    if (log.isDebugEnabled()) {
                        log.debug("User " + user.getUsername() + " has permission " + permissionName + " to class " + domainCanonicalClassName + " based on the global security settings.");
                    }
                    return true;
                }
            }
        }

        return false;
    }

    public PlatformUser getAuthorizedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return (PlatformUser) authentication.getPrincipal();
        }
        return null;
    }

    public boolean hasPermission(String username, SecurableModel securibleObject, String permissionName) {
        final User user = userService.find(username);

        final boolean hasPermission = hasUserPermissionToObject(user, PermissionName.valueOf(permissionName), securibleObject);
        log.debug("hasPermission: " + hasPermission);

        return hasPermission;
    }

}
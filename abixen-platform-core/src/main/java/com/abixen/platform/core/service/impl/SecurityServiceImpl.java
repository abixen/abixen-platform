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

import com.abixen.platform.common.model.SecurableModel;
import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.AclSidType;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.AclEntry;
import com.abixen.platform.core.model.impl.Permission;
import com.abixen.platform.core.model.impl.Role;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.core.repository.AclEntryRepository;
import com.abixen.platform.common.security.PlatformUser;
import com.abixen.platform.core.service.SecurityService;
import com.abixen.platform.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;
    private final AclEntryRepository aclEntryRepository;

    @Autowired
    public SecurityServiceImpl(UserService userService,
                               AclEntryRepository aclEntryRepository) {
        this.userService = userService;
        this.aclEntryRepository = aclEntryRepository;

    }

    @Override
    public Boolean hasUserPermissionToObject(User user, PermissionName permissionName, SecurableModel securableModel) {
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

        List<Long> userRoleIds = new ArrayList<>();
        for (Role role : user.getRoles()) {
            userRoleIds.add(role.getId());
        }
        List<AclEntry> rolesAclEntries = aclEntryRepository.findAll(permissionName, AclSidType.ROLE, userRoleIds, AclClassName.getByName(securableModel.getClass().getCanonicalName()), securableModel.getId());

        if (rolesAclEntries.size() > 0) {
            if (log.isDebugEnabled()) {
                log.debug("User " + user.getUsername() + " has permission " + permissionName + " to object " + securableModel.getClass().getCanonicalName() + "[id=" + securableModel.getId() + "] based on the ACL security settings.");
            }
            return true;
        }

        if (securableModel.getCreatedBy() != null && securableModel.getCreatedBy().getId().equals(user.getId())) {
            List<AclEntry> ownerAclEntries = aclEntryRepository.findAll(permissionName, AclSidType.OWNER, 0L, AclClassName.getByName(securableModel.getClass().getCanonicalName()), securableModel.getId());
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

    @Override
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

    @Override
    public Boolean hasUserRole(User user, Role role) {
        return user.getRoles().contains(role);
    }

    @Override
    public PlatformUser getAuthorizedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return (PlatformUser) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public boolean hasPermission(String username, SecurableModel securibleObject, String permissionName) {
        User user = userService.findUser(username);

        boolean hasPermission = hasUserPermissionToObject(user, PermissionName.valueOf(permissionName), securibleObject);
        log.debug("hasPermission: " + hasPermission);

        return hasPermission;
    }
}
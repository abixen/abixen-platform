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
import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.core.application.converter.PermissionToPermissionDtoConverter;
import com.abixen.platform.core.application.converter.RoleToRoleDtoConverter;
import com.abixen.platform.core.application.dto.AclPermissionDto;
import com.abixen.platform.core.application.dto.AclRolePermissionsDto;
import com.abixen.platform.core.application.dto.AclRolesPermissionsDto;
import com.abixen.platform.core.application.dto.PermissionDto;
import com.abixen.platform.core.domain.model.AclClass;
import com.abixen.platform.core.domain.model.AclEntry;
import com.abixen.platform.core.domain.model.AclObjectIdentity;
import com.abixen.platform.core.domain.model.AclSid;
import com.abixen.platform.core.domain.model.Permission;
import com.abixen.platform.core.domain.model.PermissionAclClassCategory;
import com.abixen.platform.core.domain.model.Role;
import com.abixen.platform.core.domain.service.AclClassService;
import com.abixen.platform.core.domain.service.AclEntryService;
import com.abixen.platform.core.domain.service.AclObjectIdentityService;
import com.abixen.platform.core.domain.service.AclSidService;
import com.abixen.platform.core.domain.service.PermissionAclClassCategoryService;
import com.abixen.platform.core.domain.service.PermissionService;
import com.abixen.platform.core.domain.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@PlatformApplicationService
public class AclManagementService {

    private final AclEntryService aclEntryService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final PermissionAclClassCategoryService permissionAclClassCategoryService;
    private final AclClassService aclClassService;
    private final AclSidService aclSidService;
    private final AclObjectIdentityService aclObjectIdentityService;
    private final RoleToRoleDtoConverter roleToRoleDtoConverter;
    private final PermissionToPermissionDtoConverter permissionToPermissionDtoConverter;

    @Autowired
    public AclManagementService(AclEntryService aclEntryService,
                                RoleService roleService,
                                PermissionService permissionService,
                                PermissionAclClassCategoryService permissionAclClassCategoryService,
                                AclClassService aclClassService,
                                AclSidService aclSidService,
                                AclObjectIdentityService aclObjectIdentityService,
                                RoleToRoleDtoConverter roleToRoleDtoConverter,
                                PermissionToPermissionDtoConverter permissionToPermissionDtoConverter) {
        this.aclEntryService = aclEntryService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.permissionAclClassCategoryService = permissionAclClassCategoryService;
        this.aclClassService = aclClassService;
        this.aclSidService = aclSidService;
        this.aclObjectIdentityService = aclObjectIdentityService;
        this.roleToRoleDtoConverter = roleToRoleDtoConverter;
        this.permissionToPermissionDtoConverter = permissionToPermissionDtoConverter;
    }

    public AclRolesPermissionsDto findAclRolesPermissions(final String permissionAclClassCategoryName, final Long objectId) {
        log.debug("findAclRolesPermissions() - permissionAclClassCategoryName: {}, objectId: {}", permissionAclClassCategoryName, objectId);

        final PermissionAclClassCategory permissionAclClassCategory = permissionAclClassCategoryService.find(permissionAclClassCategoryName);

        return getAclRolesPermissionsDto(permissionAclClassCategory, objectId);
    }

    @Transactional
    public AclRolesPermissionsDto updateAclRolesPermissions(final AclRolesPermissionsDto aclRolesPermissionsDto, final String permissionAclClassCategoryName, final Long objectId) {
        log.debug("updateAclRolesPermissions() - permissionAclClassCategoryName: {}, objectId: {}, aclRolesPermissionsDto: {}", permissionAclClassCategoryName, objectId, aclRolesPermissionsDto);

        final PermissionAclClassCategory permissionAclClassCategory = permissionAclClassCategoryService.find(permissionAclClassCategoryName);

        for (AclRolePermissionsDto aclRolePermissionsDto : aclRolesPermissionsDto.getAclRolePermissionsDtos()) {
            log.debug("aclRolePermissionsDto: " + aclRolePermissionsDto.getRole());

            for (AclPermissionDto aclPermissionDto : aclRolePermissionsDto.getAclPermissionDtos()) {
                log.debug("aclPermissionDto - selected: " + aclPermissionDto.isSelected() + ", selectable: " + aclPermissionDto.isSelectable());
            }
        }

        final AclClassName aclClassName = permissionAclClassCategory.getAclClass().getAclClassName();
        final AclClass aclClass = aclClassService.find(aclClassName);

        final List<Role> roles = roleService.findAll();

        for (AclRolePermissionsDto aclRolePermissionsDto : aclRolesPermissionsDto.getAclRolePermissionsDtos()) {
            final List<Long> newPermissionIds = new ArrayList<>();
            Role checkedRole = null;
            for (Role role : roles) {

                if (aclRolePermissionsDto.getRole() != null) {
                    if (role.getId().equals(aclRolePermissionsDto.getRole().getId())) {
                        checkedRole = role;
                    }
                }

            }

            for (AclPermissionDto aclPermissionDto : aclRolePermissionsDto.getAclPermissionDtos()) {

                if (checkedRole != null) {
                    final boolean roleHasPermission = checkedRole.getPermissions().contains(aclPermissionDto.getPermission());

                    if (!roleHasPermission) {
                        if (aclPermissionDto.isSelected()) {
                            newPermissionIds.add(aclPermissionDto.getPermission().getId());
                        }

                    }
                } else {
                    if (aclPermissionDto.isSelected()) {
                        newPermissionIds.add(aclPermissionDto.getPermission().getId());
                    }
                }


            }

            final AclObjectIdentity aclObjectIdentity = aclObjectIdentityService.find(aclClass, objectId);
            final AclSid aclSid = aclSidService.find(checkedRole != null ? AclSidType.ROLE : AclSidType.OWNER, checkedRole != null ? checkedRole.getId() : 0);

            aclEntryService.deleteAll(aclObjectIdentity, aclSid);

            for (Long permissionId : newPermissionIds) {
                final AclEntry aclEntry = AclEntry.builder()
                        .aclSid(aclSid)
                        .permission(permissionService.find(permissionId))
                        .aclObjectIdentity(aclObjectIdentity)
                        .build();

                aclEntryService.create(aclEntry);
            }

        }

        return getAclRolesPermissionsDto(permissionAclClassCategory, objectId);
    }

    private AclRolesPermissionsDto getAclRolesPermissionsDto(final PermissionAclClassCategory permissionAclClassCategory, final Long objectId) {
        final List<Permission> permissions = permissionService.findAll(permissionAclClassCategory);
        final List<Role> roles = roleService.findAll();

        final List<AclRolePermissionsDto> aclRolePermissionsDtos = new ArrayList<>();

        for (Role role : roles) {
            final AclRolePermissionsDto aclRolePermissionsDto = new AclRolePermissionsDto();
            aclRolePermissionsDto.setRole(roleToRoleDtoConverter.convert(role));
            for (Permission permission : permissions) {
                boolean roleHasPermission = role.getPermissions().contains(permission);
                boolean permissionCanBeOverriddenByAcl = !roleHasPermission;
                PermissionDto permissionDto = permissionToPermissionDtoConverter.convert(permission);
                AclPermissionDto aclPermissionDto = new AclPermissionDto(permissionDto, roleHasPermission, permissionCanBeOverriddenByAcl);
                aclRolePermissionsDto.getAclPermissionDtos().add(aclPermissionDto);
            }
            aclRolePermissionsDtos.add(aclRolePermissionsDto);
        }

        final AclClassName aclClassName = permissionAclClassCategory.getAclClass().getAclClassName();
        final List<AclEntry> aclEntries = aclEntryService.findAll(aclClassName, objectId);
        log.debug("aclEntries: " + aclEntries);

        //For owner
        final AclRolePermissionsDto aclRoleOwnerPermissionsDto = new AclRolePermissionsDto();
        for (Permission permission : permissions) {
            final PermissionDto permissionDto = permissionToPermissionDtoConverter.convert(permission);
            final AclPermissionDto aclPermissionDto = new AclPermissionDto(permissionDto, false, true);
            aclRoleOwnerPermissionsDto.getAclPermissionDtos().add(aclPermissionDto);
        }
        aclRolePermissionsDtos.add(aclRoleOwnerPermissionsDto);

        for (AclRolePermissionsDto aclRolePermissionsDto : aclRolePermissionsDtos) {
            Long roleId = null;
            if (aclRolePermissionsDto.getRole() != null) {
                roleId = aclRolePermissionsDto.getRole().getId();
            }
            final List<AclEntry> filteredAclEntries = filterAclEntriesByRoleName(aclEntries, roleId);
            for (AclPermissionDto aclPermissionDto : aclRolePermissionsDto.getAclPermissionDtos()) {
                for (AclEntry aclEntry : filteredAclEntries) {
                    if (aclPermissionDto.getPermission().getId().equals(aclEntry.getPermission().getId())) {
                        aclPermissionDto.setSelected(true);
                    }
                }
            }
        }

        final List<PermissionDto> permissionDtos = permissionToPermissionDtoConverter.convertToList(permissions);
        return new AclRolesPermissionsDto(aclRolePermissionsDtos, permissionDtos);
    }

    private List<AclEntry> filterAclEntriesByRoleName(List<AclEntry> aclEntries, Long roleId) {
        final List<AclEntry> filteredAclEntries = new ArrayList<>();

        if (roleId != null) {
            for (AclEntry aclEntry : aclEntries) {
                if (aclEntry.getAclSid().getSidType().equals(AclSidType.ROLE) && aclEntry.getAclSid().getSidId() == roleId) {
                    filteredAclEntries.add(aclEntry);
                }
            }
        } else {
            for (AclEntry aclEntry : aclEntries) {
                if (aclEntry.getAclSid().getSidType().equals(AclSidType.OWNER)) {
                    filteredAclEntries.add(aclEntry);
                }
            }
        }

        return filteredAclEntries;
    }

}
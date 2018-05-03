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

package com.abixen.platform.core.domain.service;

import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.AclSidType;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.common.infrastructure.annotation.PlatformDomainService;
import com.abixen.platform.core.domain.model.AclClass;
import com.abixen.platform.core.domain.model.AclEntry;
import com.abixen.platform.core.domain.model.AclObjectIdentity;
import com.abixen.platform.core.domain.model.AclSid;
import com.abixen.platform.core.domain.model.SecurableModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@PlatformDomainService
public class AclService {

    private final PermissionService permissionService;
    private final AclEntryService aclEntryService;
    private final AclClassService aclClassService;
    private final AclObjectIdentityService aclObjectIdentityService;
    private final AclSidService aclSidService;

    @Autowired
    public AclService(PermissionService permissionService,
                      AclEntryService aclEntryService,
                      AclClassService aclClassService,
                      AclObjectIdentityService aclObjectIdentityService,
                      AclSidService aclSidService) {
        this.permissionService = permissionService;
        this.aclEntryService = aclEntryService;
        this.aclClassService = aclClassService;
        this.aclObjectIdentityService = aclObjectIdentityService;
        this.aclSidService = aclSidService;
    }


    public void createDefaultAcl(final SecurableModel securableModel, final List<PermissionName> permissionNames) {
        log.debug("createDefaultAcl() - securableModel: {}, permissionNames: {}", securableModel, permissionNames);

        final AclClass aclClass = aclClassService.find(AclClassName.getByName(securableModel.getClass().getCanonicalName()));
        final AclSid ownerAclSid = aclSidService.find(AclSidType.OWNER, 0L);
        AclObjectIdentity aclObjectIdentity = aclObjectIdentityService.find(aclClass, securableModel.getId());

        if (aclObjectIdentity == null) {
            aclObjectIdentity = AclObjectIdentity.builder()
                    .aclClass(aclClass)
                    .objectId(securableModel.getId())
                    .build();
        }

        for (final PermissionName permissionName : permissionNames) {
            final AclEntry aclEntry = AclEntry.builder()
                    .aclSid(ownerAclSid)
                    .permission(permissionService.find(permissionName))
                    .aclObjectIdentity(aclObjectIdentity)
                    .build();

            aclEntryService.create(aclEntry);
        }
    }

}
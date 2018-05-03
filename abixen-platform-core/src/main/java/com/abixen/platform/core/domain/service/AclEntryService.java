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
import com.abixen.platform.core.domain.model.AclEntry;
import com.abixen.platform.core.domain.model.AclObjectIdentity;
import com.abixen.platform.core.domain.model.AclSid;
import com.abixen.platform.core.domain.repository.AclEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@PlatformDomainService
public class AclEntryService {

    private final AclEntryRepository aclEntryRepository;

    @Autowired
    public AclEntryService(AclEntryRepository aclEntryRepository) {
        this.aclEntryRepository = aclEntryRepository;
    }

    public List<AclEntry> findAll(final AclClassName aclClassName, final Long objectId) {
        log.debug("findAll() - aclClassName: {}, objectId: {}", aclClassName, objectId);

        return aclEntryRepository.findAll();
    }

    public List<AclEntry> findAll(PermissionName permissionName, AclSidType aclSidType, Long sidId, AclClassName aclClassName, Long objectId) {
        log.debug("findAll() - permissionName: {}, aclSidType: {}, sidId: {}, aclClassName: {}, objectId: {}", permissionName, aclSidType, sidId, aclClassName, objectId);

        return aclEntryRepository.findAll(permissionName, aclSidType, sidId, aclClassName, objectId);
    }

    public List<AclEntry> findAll(PermissionName permissionName, AclSidType aclSidType, List<Long> sidIds, AclClassName aclClassName, Long objectId) {
        log.debug("findAll() - permissionName: {}, aclSidType: {}, sidIds: {}, aclClassName: {}, objectId: {}", permissionName, aclSidType, sidIds, aclClassName, objectId);

        return aclEntryRepository.findAll(permissionName, aclSidType, sidIds, aclClassName, objectId);
    }

    public AclEntry create(final AclEntry aclEntry) {
        log.debug("create() - aclEntry: {}", aclEntry);

        return aclEntryRepository.save(aclEntry);
    }

    public void deleteAll(final AclObjectIdentity aclObjectIdentity, final AclSid aclSid) {
        log.debug("deleteAll() - aclObjectIdentity: {}, aclSid: {}", aclObjectIdentity, aclSid);

        aclEntryRepository.deleteAll(aclObjectIdentity, aclSid);
    }

}
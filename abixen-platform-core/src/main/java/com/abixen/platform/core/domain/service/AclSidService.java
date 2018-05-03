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

import com.abixen.platform.common.domain.model.enumtype.AclSidType;
import com.abixen.platform.common.infrastructure.annotation.PlatformDomainService;
import com.abixen.platform.core.domain.model.AclSid;
import com.abixen.platform.core.domain.repository.AclSidRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@PlatformDomainService
public class AclSidService {

    private final AclSidRepository aclSidRepository;

    @Autowired
    public AclSidService(AclSidRepository aclSidRepository) {
        this.aclSidRepository = aclSidRepository;
    }


    public AclSid find(final AclSidType aclSidType, final Long sidId) {
        log.debug("find() - aclSidType: {}, sidId: {}", aclSidType, sidId);

        return aclSidRepository.find(aclSidType, sidId);
    }

    public AclSid create(final AclSidType aclSidType, final Long sidId) {
        log.debug("create() - aclSidType: {}, sidId: {}", aclSidType, sidId);

        AclSid aclSid = AclSid.builder()
                .sidId(sidId)
                .aclSidType(aclSidType)
                .build();

        return aclSidRepository.save(aclSid);
    }

}
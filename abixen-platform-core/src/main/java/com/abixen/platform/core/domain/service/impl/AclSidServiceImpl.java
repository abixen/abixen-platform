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

package com.abixen.platform.core.domain.service.impl;

import com.abixen.platform.common.domain.model.enumtype.AclSidType;
import com.abixen.platform.core.domain.model.AclSid;
import com.abixen.platform.core.domain.model.AclSidBuilder;
import com.abixen.platform.core.domain.repository.custom.AclSidRepository;
import com.abixen.platform.core.domain.service.AclSidService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class AclSidServiceImpl implements AclSidService {

    private final AclSidRepository aclSidRepository;

    @Autowired
    public AclSidServiceImpl(AclSidRepository aclSidRepository) {
        this.aclSidRepository = aclSidRepository;
    }


    @Override
    public AclSid find(final AclSidType aclSidType, final Long sidId) {
        log.debug("find() - aclSidType: {}, sidId: {}", aclSidType, sidId);

        return aclSidRepository.findBySidTypeAndSidId(aclSidType, sidId);
    }

    @Override
    public AclSid create(final AclSidType aclSidType, final Long sidId) {
        log.debug("create() - aclSidType: {}, sidId: {}", aclSidType, sidId);

        AclSid aclSid = new AclSidBuilder()
                .sidId(sidId)
                .aclSidType(aclSidType)
                .build();

        return aclSidRepository.save(aclSid);
    }

}
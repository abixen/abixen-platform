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

import com.abixen.platform.common.model.enumtype.AclSidType;
import com.abixen.platform.core.model.impl.AclSid;
import com.abixen.platform.core.repository.custom.AclSidRepository;
import com.abixen.platform.core.service.AclSidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AclSidServiceImpl implements AclSidService {

    private final AclSidRepository aclSidRepository;

    @Autowired
    public AclSidServiceImpl(AclSidRepository aclSidRepository) {
        this.aclSidRepository = aclSidRepository;
    }

    @Override
    public AclSid createAclSid(AclSidType aclSidType, Long sidId) {
        AclSid aclSid = new AclSid();
        aclSid.setSidId(sidId);
        aclSid.setSidType(aclSidType);
        return aclSidRepository.save(aclSid);
    }
}
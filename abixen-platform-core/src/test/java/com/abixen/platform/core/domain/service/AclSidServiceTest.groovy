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

package com.abixen.platform.core.domain.service

import com.abixen.platform.common.domain.model.enumtype.AclSidType
import com.abixen.platform.core.domain.model.AclSid
import com.abixen.platform.core.domain.repository.AclSidRepository
import spock.lang.Specification


class AclSidServiceTest extends Specification {

    private AclSidService aclSidService;
    private AclSidRepository aclSidRepository

    void setup() {
        aclSidRepository = Mock()
        aclSidService = new AclSidService(aclSidRepository)
    }

    void "should create AclSid"() {
        given:
        final AclSidType aclSidType = AclSidType.OWNER
        final Long sidId = 1L

        final AclSid aclSid = AclSid.builder()
                .sidId(sidId)
                .aclSidType(aclSidType)
                .build();

        aclSidRepository.save(aclSid) >> aclSid

        when:
        AclSid createdAclSid = aclSidService.create(aclSidType, sidId)

        then:
        createdAclSid.sidId == sidId
        createdAclSid.sidType == aclSidType
        1 * aclSidRepository.save(aclSid) >> aclSid
        0 * _
    }

}
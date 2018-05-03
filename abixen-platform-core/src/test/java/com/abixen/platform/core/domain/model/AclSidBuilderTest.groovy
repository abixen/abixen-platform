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
package com.abixen.platform.core.domain.model

import com.abixen.platform.common.domain.model.enumtype.AclSidType

import spock.lang.Specification

class AclSidBuilderTest extends Specification {

    void "should build Acl Sid entity"() {
        given:
        final long sidId = 10
        final AclSidType sidType = AclSidType.OWNER

        when:
        AclSid aclSid = AclSid.builder()
                .sidId(sidId)
                .aclSidType(sidType)
                .build()

        then:
        aclSid.sidId == sidId
        aclSid.sidType == sidType
    }

}

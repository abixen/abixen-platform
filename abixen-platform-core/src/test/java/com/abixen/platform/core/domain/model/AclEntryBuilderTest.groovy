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
import com.abixen.platform.common.domain.model.enumtype.PermissionName
import spock.lang.Specification

class AclEntryBuilderTest extends Specification {


    void "should build acl entry "() {
        given:
        final Permission permission = new Permission();
        final PermissionName PERMISSIONNAME = PermissionName.MODULE_ADD;
        final String DESCRIPTION = "test_description"
        permission.setPermissionName(PERMISSIONNAME);
        permission.setDescription(DESCRIPTION)
        final AclObjectIdentity aclObjectIdentity = new AclObjectIdentity();
        final AclSid aclSid = new AclSid();
        aclSid.sidType = AclSidType.ROLE;

        when:
        final AclEntry aclEntry = AclEntry.builder()
                .permission(permission)
                .aclObjectIdentity(aclObjectIdentity)
                .aclSid(aclSid)
                .build()

        then:
        aclEntry.permission == permission
        aclEntry.aclObjectIdentity == aclObjectIdentity
        aclEntry.aclSid == aclSid
        aclSid.sidType == AclSidType.ROLE
        aclEntry.permission.permissionName == PERMISSIONNAME
        aclEntry.permission.description == DESCRIPTION
    }
}

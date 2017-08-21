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

package com.abixen.platform.core.domain.repository.custom;

import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.AclSidType;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.domain.model.AclEntry;
import com.abixen.platform.core.domain.model.AclObjectIdentity;
import com.abixen.platform.core.domain.model.AclSid;

import java.util.List;


public interface AclEntryRepositoryCustom {

    //FIXME - should return only one object
    List<AclEntry> findAll(PermissionName permissionName, AclSidType aclSidType, Long sidId, AclClassName aclClassName, Long objectId);

    List<AclEntry> findAll(PermissionName permissionName, AclSidType aclSidType, List<Long> sidIds, AclClassName aclClassName, Long objectId);

    List<AclEntry> findAll(AclClassName aclClassName, Long objectId);

    int removeAclEntries(AclObjectIdentity aclObjectIdentity, AclSid aclSid);

}
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

package com.abixen.platform.core.repository.custom.impl;

import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.AclSidType;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.AclEntry;
import com.abixen.platform.core.model.impl.AclObjectIdentity;
import com.abixen.platform.core.model.impl.AclSid;
import com.abixen.platform.core.repository.custom.AclEntryRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
public class AclEntryRepositoryImpl implements AclEntryRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    /**
     * Use this method to find all Acl Entries for aclSidType equals OWNER.
     * In the above case sidId is the only one.
     *
     * @param permissionName
     * @param aclSidType
     * @param sidId
     * @param aclClassName
     * @param objectId
     * @return
     */
    @Override
    public List<AclEntry> findAll(PermissionName permissionName, AclSidType aclSidType, Long sidId, AclClassName aclClassName, Long objectId) {
        List<Long> sidIds = new ArrayList<>();
        sidIds.add(sidId);
        return findAll(permissionName, aclSidType, sidIds, aclClassName, objectId);
    }

    @Override
    public List<AclEntry> findAll(PermissionName permissionName, AclSidType aclSidType, List<Long> sidIds, AclClassName aclClassName, Long objectId) {
        log.debug("findAll()");
        log.debug("permissionName: " + permissionName);
        log.debug("aclSidType: " + aclSidType);
        log.debug("sidIds: " + sidIds);
        log.debug("aclClassName: " + aclClassName);
        log.debug("objectId: " + objectId);

        Query query = entityManager.createQuery("SELECT ae FROM AclEntry ae WHERE " +
                "ae.permission.permissionName = :permissionName AND " +
                "ae.aclSid.sidType = :aclSidType AND " +
                "ae.aclSid.sidId IN (:sidIds) AND " +
                "ae.aclObjectIdentity.aclClass.aclClassName = :aclClassName AND " +
                "ae.aclObjectIdentity.objectId = :objectId");
        query.setParameter("permissionName", permissionName);
        query.setParameter("aclSidType", aclSidType);
        query.setParameter("sidIds", sidIds);
        query.setParameter("aclClassName", aclClassName);
        query.setParameter("objectId", objectId);
        return query.getResultList();
    }

    @Override
    public List<AclEntry> findAll(AclClassName aclClassName, Long objectId) {
        log.debug("findAll()");
        log.debug("aclClassName: " + aclClassName);
        log.debug("objectId: " + objectId);

        Query query = entityManager.createQuery("SELECT ae FROM AclEntry ae WHERE " +
                "ae.aclObjectIdentity.aclClass.aclClassName = :aclClassName AND " +
                "ae.aclObjectIdentity.objectId = :objectId");
        query.setParameter("aclClassName", aclClassName);
        query.setParameter("objectId", objectId);
        return query.getResultList();
    }


    @Override
    public int removeAclEntries(AclObjectIdentity aclObjectIdentity, AclSid aclSid) {
        log.debug("updateAclEntries()");
        log.debug("aclObjectIdentity: " + aclObjectIdentity);
        log.debug("aclSid: " + aclSid);

        Query deleteQuery = entityManager.createQuery("DELETE FROM AclEntry ae WHERE " +
                "ae.aclSid = :aclSid AND " +
                "ae.aclObjectIdentity = :aclObjectIdentity ");
        deleteQuery.setParameter("aclSid", aclSid);
        deleteQuery.setParameter("aclObjectIdentity", aclObjectIdentity);
        int removedAclEntries = deleteQuery.executeUpdate();
        log.debug("removedAclEntries: " + removedAclEntries);
        return removedAclEntries;
    }
}

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

package com.abixen.platform.core.infrastructure.repository.custom.impl;

import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.AclSidType;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.core.domain.model.AclEntry;
import com.abixen.platform.core.domain.model.AclObjectIdentity;
import com.abixen.platform.core.domain.model.AclSid;
import com.abixen.platform.core.domain.repository.custom.AclEntryRepositoryCustom;
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


    private final EntityManager entityManager;

    @Autowired
    public AclEntryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
    public List<AclEntry> findAll(final PermissionName permissionName, final AclSidType aclSidType, final Long sidId, final AclClassName aclClassName, final Long objectId) {
        final List<Long> sidIds = new ArrayList<>();
        sidIds.add(sidId);

        return findAll(permissionName, aclSidType, sidIds, aclClassName, objectId);
    }

    @Override
    public List<AclEntry> findAll(final PermissionName permissionName, final AclSidType aclSidType, final List<Long> sidIds, final AclClassName aclClassName, final Long objectId) {
        log.debug("findAll()");

        final Query query = entityManager.createQuery("SELECT ae FROM AclEntry ae WHERE " +
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
    public List<AclEntry> findAll(final AclClassName aclClassName, final Long objectId) {
        final Query query = entityManager.createQuery("SELECT ae FROM AclEntry ae WHERE " +
                "ae.aclObjectIdentity.aclClass.aclClassName = :aclClassName AND " +
                "ae.aclObjectIdentity.objectId = :objectId");
        query.setParameter("aclClassName", aclClassName);
        query.setParameter("objectId", objectId);

        return query.getResultList();
    }


    @Override
    public void deleteAll(final AclObjectIdentity aclObjectIdentity, final AclSid aclSid) {
        final Query deleteQuery = entityManager.createQuery("DELETE FROM AclEntry ae WHERE " +
                "ae.aclSid = :aclSid AND " +
                "ae.aclObjectIdentity = :aclObjectIdentity ");
        deleteQuery.setParameter("aclSid", aclSid);
        deleteQuery.setParameter("aclObjectIdentity", aclObjectIdentity);
        int removedAclEntries = deleteQuery.executeUpdate();
        log.debug("removedAclEntries: " + removedAclEntries);
    }

}
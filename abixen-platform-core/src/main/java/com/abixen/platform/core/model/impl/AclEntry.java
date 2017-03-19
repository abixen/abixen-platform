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

package com.abixen.platform.core.model.impl;

import com.abixen.platform.common.model.AclEntryBase;

import javax.persistence.*;


@Entity
@Table(name = "acl_entry")
@SequenceGenerator(sequenceName = "acl_entry_seq", name = "acl_entry_seq", allocationSize = 1)
public class AclEntry extends AuditingModel implements AclEntryBase<Permission, AclObjectIdentity, AclSid> {


    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "acl_entry_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    @JoinColumn(name = "acl_object_identity_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private AclObjectIdentity aclObjectIdentity;

    @JoinColumn(name = "acl_sid_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private AclSid aclSid;

    @Override
    public Permission getPermission() {
        return permission;
    }

    @Override
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public AclObjectIdentity getAclObjectIdentity() {
        return aclObjectIdentity;
    }

    @Override
    public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
        this.aclObjectIdentity = aclObjectIdentity;
    }

    @Override
    public AclSid getAclSid() {
        return aclSid;
    }

    @Override
    public void setAclSid(AclSid aclSid) {
        this.aclSid = aclSid;
    }
}

/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.core.domain.model.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "acl_entry")
@SequenceGenerator(sequenceName = "acl_entry_seq", name = "acl_entry_seq", allocationSize = 1)
public class AclEntry extends AuditingModel {


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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public AclObjectIdentity getAclObjectIdentity() {
        return aclObjectIdentity;
    }

    public void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
        this.aclObjectIdentity = aclObjectIdentity;
    }

    public AclSid getAclSid() {
        return aclSid;
    }

    public void setAclSid(AclSid aclSid) {
        this.aclSid = aclSid;
    }
}

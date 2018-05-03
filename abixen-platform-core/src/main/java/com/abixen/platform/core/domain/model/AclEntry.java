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

package com.abixen.platform.core.domain.model;

import com.abixen.platform.common.domain.model.EntityBuilder;

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
public final class AclEntry extends AuditingModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

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

    private AclEntry() {
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Permission getPermission() {
        return permission;
    }

    private void setPermission(Permission permission) {
        this.permission = permission;
    }

    public AclObjectIdentity getAclObjectIdentity() {
        return aclObjectIdentity;
    }

    private void setAclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
        this.aclObjectIdentity = aclObjectIdentity;
    }

    public AclSid getAclSid() {
        return aclSid;
    }

    private void setAclSid(AclSid aclSid) {
        this.aclSid = aclSid;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<AclEntry> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new AclEntry();
        }

        public Builder permission(Permission permission) {
            this.product.setPermission(permission);
            return this;
        }

        public Builder aclObjectIdentity(AclObjectIdentity aclObjectIdentity) {
            this.product.setAclObjectIdentity(aclObjectIdentity);
            return this;
        }

        public Builder aclSid(AclSid aclSid) {
            this.product.setAclSid(aclSid);
            return this;
        }
    }

}
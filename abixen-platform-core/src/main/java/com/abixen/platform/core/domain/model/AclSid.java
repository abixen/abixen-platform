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
import com.abixen.platform.common.domain.model.Model;
import com.abixen.platform.common.domain.model.enumtype.AclSidType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "acl_sid")
@SequenceGenerator(sequenceName = "acl_sid_seq", name = "acl_sid_seq", allocationSize = 1)
public final class AclSid extends Model {

    /**
     *
     */
    private static final long serialVersionUID = -3048423211918839723L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "acl_sid_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "sid_type", nullable = false)
    private AclSidType sidType;

    /**
     * If sidType is equal ROLE then sidId is an id of role.
     * If sidType is equal OWNER then sidId is 0.
     */
    @Column(name = "sid_id", nullable = false)
    private Long sidId;

    private AclSid() {
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public AclSidType getSidType() {
        return sidType;
    }

    private void setSidType(AclSidType sidType) {
        this.sidType = sidType;
    }

    public Long getSidId() {
        return sidId;
    }

    private void setSidId(Long sidId) {
        this.sidId = sidId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<AclSid> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new AclSid();
        }

        public Builder sidId(Long sidId) {
            this.product.setSidId(sidId);
            return this;
        }

        public Builder aclSidType(AclSidType sidType) {
            this.product.setSidType(sidType);
            return this;
        }
    }

}
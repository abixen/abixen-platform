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

import com.abixen.platform.common.model.AclSidBase;
import com.abixen.platform.common.model.Model;
import com.abixen.platform.common.model.enumtype.AclSidType;

import javax.persistence.*;


@Entity
@Table(name = "acl_sid")
@SequenceGenerator(sequenceName = "acl_sid_seq", name = "acl_sid_seq", allocationSize = 1)
public class AclSid extends Model implements AclSidBase {

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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public AclSidType getSidType() {
        return sidType;
    }

    @Override
    public void setSidType(AclSidType sidType) {
        this.sidType = sidType;
    }

    @Override
    public Long getSidId() {
        return sidId;
    }

    @Override
    public void setSidId(Long sidId) {
        this.sidId = sidId;
    }

}

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

import com.abixen.platform.common.model.Model;

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
@Table(name = "acl_object_identity")
@SequenceGenerator(sequenceName = "acl_object_identity_seq", name = "acl_object_identity_seq", allocationSize = 1)
public class AclObjectIdentity extends Model {

    /**
     *
     */
    private static final long serialVersionUID = -3148427281918839723L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "acl_object_identity_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @JoinColumn(name = "acl_class_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private AclClass aclClass;

    /**
     * Id of domain class represented by aclClass field.
     */
    @Column(name = "object_id", nullable = false)
    private Long objectId;

    AclObjectIdentity() {
    }

    @Override
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public AclClass getAclClass() {
        return aclClass;
    }

    void setAclClass(AclClass aclClass) {
        this.aclClass = aclClass;
    }

    public Long getObjectId() {
        return objectId;
    }

    void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

}
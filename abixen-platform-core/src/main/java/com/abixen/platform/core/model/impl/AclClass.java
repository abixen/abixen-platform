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

import com.abixen.platform.common.model.AclClassBase;
import com.abixen.platform.common.model.Model;
import com.abixen.platform.common.model.enumtype.AclClassName;

import javax.persistence.*;


@Entity
@Table(name = "acl_class")
@SequenceGenerator(sequenceName = "acl_class_seq", name = "acl_class_seq", allocationSize = 1)
public class AclClass extends Model implements AclClassBase {

    /**
     *
     */
    private static final long serialVersionUID = -3518427281918839763L;


    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "acl_class_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Represents a canonical name of domain class.
     * E.g. com.abixen.platform.core.model.impl.User
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private AclClassName aclClassName;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public AclClassName getAclClassName() {
        return aclClassName;
    }

    @Override
    public void setAclClassName(AclClassName aclClassName) {
        this.aclClassName = aclClassName;
    }

}
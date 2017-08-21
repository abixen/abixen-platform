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
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "permission_acl_class_category", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@SequenceGenerator(sequenceName = "permission_acl_class_category_seq", name = "permission_acl_class_category_seq", allocationSize = 1)
public class PermissionAclClassCategory extends Model {

    /**
     *
     */
    private static final long serialVersionUID = -3048421211918839723L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "permission_acl_class_category_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @JoinColumn(name = "acl_class_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private AclClass aclClass;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    PermissionAclClassCategory() {
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

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

}
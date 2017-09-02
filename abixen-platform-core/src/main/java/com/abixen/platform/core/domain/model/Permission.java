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


import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@JsonSerialize(as = Permission.class)
@Entity
@Table(name = "permission", uniqueConstraints = @UniqueConstraint(columnNames = {"permission_name"}))
@SequenceGenerator(sequenceName = "permission_seq", name = "permission_seq", allocationSize = 1)
public class Permission extends AuditingModel {

    /**
     *
     */
    private static final long serialVersionUID = -1247915702100609224L;

    public static final int TITLE_MIN_LENGTH = 6;
    public static final int TITLE_MAX_LENGTH = 40;
    public static final int DESCRIPTION_MIN_LENGTH = 100;
    public static final int DESCRIPTION_MAX_LENGTH = 100;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "permission_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_name", nullable = false)
    private PermissionName permissionName;

    @Column(name = "title", length = TITLE_MAX_LENGTH, nullable = false)
    private String title;

    @Column(name = "description", length = DESCRIPTION_MAX_LENGTH, nullable = false)
    private String description;

    @JoinColumn(name = "permission_acl_class_category_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private PermissionAclClassCategory permissionAclClassCategory;

    @JoinColumn(name = "permission_general_category_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private PermissionGeneralCategory permissionGeneralCategory;

    Permission() {
    }

    @Override
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public PermissionName getPermissionName() {
        return permissionName;
    }

    void setPermissionName(PermissionName permissionName) {
        this.permissionName = permissionName;
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

    public PermissionAclClassCategory getPermissionAclClassCategory() {
        return permissionAclClassCategory;
    }

    void setPermissionAclClassCategory(PermissionAclClassCategory permissionAclClassCategory) {
        this.permissionAclClassCategory = permissionAclClassCategory;
    }

    public PermissionGeneralCategory getPermissionGeneralCategory() {
        return permissionGeneralCategory;
    }

    void setPermissionGeneralCategory(PermissionGeneralCategory permissionGeneralCategory) {
        this.permissionGeneralCategory = permissionGeneralCategory;
    }
}
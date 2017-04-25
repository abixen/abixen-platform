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


import com.abixen.platform.common.model.PermissionBase;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;


@JsonSerialize(as = Permission.class)
@Entity
@Table(name = "permission", uniqueConstraints = @UniqueConstraint(columnNames = {"permission_name"}))
@SequenceGenerator(sequenceName = "permission_seq", name = "permission_seq", allocationSize = 1)
public class Permission extends AuditingModel implements PermissionBase<PermissionAclClassCategory, PermissionGeneralCategory> {

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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public PermissionName getPermissionName() {
        return permissionName;
    }

    @Override
    public void setPermissionName(PermissionName permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public PermissionAclClassCategory getPermissionAclClassCategory() {
        return permissionAclClassCategory;
    }

    @Override
    public void setPermissionAclClassCategory(PermissionAclClassCategory permissionAclClassCategory) {
        this.permissionAclClassCategory = permissionAclClassCategory;
    }

    @Override
    public PermissionGeneralCategory getPermissionGeneralCategory() {
        return permissionGeneralCategory;
    }

    @Override
    public void setPermissionGeneralCategory(PermissionGeneralCategory permissionGeneralCategory) {
        this.permissionGeneralCategory = permissionGeneralCategory;
    }


}

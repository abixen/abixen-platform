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

import com.abixen.platform.common.model.Model;
import com.abixen.platform.common.model.PermissionGeneralCategoryBase;

import javax.persistence.*;


@Entity
@Table(name = "permission_general_category", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@SequenceGenerator(sequenceName = "permission_general_category_seq", name = "permission_general_category_seq", allocationSize = 1)
public class PermissionGeneralCategory extends Model implements PermissionGeneralCategoryBase {

    /**
     *
     */
    private static final long serialVersionUID = -3048423211118839723L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "permission_general_category_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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

}

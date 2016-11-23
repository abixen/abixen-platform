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

import com.abixen.platform.core.model.PageBase;
import com.abixen.platform.core.model.SecurableModel;
import com.abixen.platform.core.model.web.PageWeb;
import com.abixen.platform.core.util.ModelKeys;

import javax.persistence.*;


@Entity
@Table(name = "page")
@SequenceGenerator(sequenceName = "page_seq", name = "page_seq", allocationSize = 1)
public class Page extends AuditingModel implements PageBase<Layout>, PageWeb, SecurableModel<User> {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "page_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = ModelKeys.PAGE_NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "title", length = ModelKeys.PAGE_TITLE_MAX_LENGTH, nullable = false)
    private String title;

    @Column(name = "description", length = ModelKeys.PAGE_DESCRIPTION_MAX_LENGTH)
    private String description;

    @JoinColumn(name = "layout_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Layout layout;

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

    @Override
    public Layout getLayout() {
        return layout;
    }

    @Override
    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}

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

import com.abixen.platform.common.model.PageBase;
import com.abixen.platform.common.model.SecurableModel;

import javax.persistence.*;


@Entity
@Table(name = "page")
@SequenceGenerator(sequenceName = "page_seq", name = "page_seq", allocationSize = 1)
public class Page extends AuditingModel implements PageBase<Layout>, SecurableModel<User> {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "page_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", length = PAGE_TITLE_MAX_LENGTH, nullable = false)
    private String title;

    @Column(name = "icon", length = PAGE_ICON_MAX_LENGTH, nullable = false)
    private String icon;

    @Column(name = "description", length = PAGE_DESCRIPTION_MAX_LENGTH)
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
    public String getIcon() {
        return icon;
    }

    @Override
    public void setIcon(String icon) {
        this.icon = icon;
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
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
@Table(name = "page")
@SequenceGenerator(sequenceName = "page_seq", name = "page_seq", allocationSize = 1)
public class Page extends AuditingModel implements SecurableModel {

    public static final int PAGE_TITLE_MAX_LENGTH = 40;
    public static final int PAGE_ICON_MAX_LENGTH = 40;
    public static final int PAGE_DESCRIPTION_MAX_LENGTH = 255;

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

    Page() {
    }

    @Override
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    void setIcon(String icon) {
        this.icon = icon;
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

    public Layout getLayout() {
        return layout;
    }

    void setLayout(Layout layout) {
        this.layout = layout;
    }

    public void changeTitle(String title) {
        setTitle(title);
    }

    public void changeLayout(Layout layout) {
        setLayout(layout);
    }

    public void changeIcon(String icon) {
        setIcon(icon);
    }

    public void changeDescription(String description) {
        setDescription(description);
    }
}
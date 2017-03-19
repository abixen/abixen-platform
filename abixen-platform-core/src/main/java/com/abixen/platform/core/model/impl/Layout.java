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

import com.abixen.platform.common.model.LayoutBase;
import com.abixen.platform.common.model.SecurableModel;

import javax.persistence.*;


@Entity
@Table(name = "layout")
@SequenceGenerator(sequenceName = "layout_seq", name = "layout_seq", allocationSize = 1)
public class Layout extends AuditingModel implements LayoutBase, SecurableModel<User> {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "layout_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", length = LAYOUT_TITLE_MAX_LENGTH, nullable = false)
    private String title;

    @Column(name = "content", length = LAYOUT_CONTENT_MAX_LENGTH, nullable = false)
    private String content;

    @Column(name = "icon_file_name", length = LAYOUT_ICON_FILE_NAME_MAX_LENGTH, nullable = false)
    private String iconFileName;

    @Transient
    private String contentAsJson;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
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
    public String getIconFileName() {
        return iconFileName;
    }

    @Override
    public void setIconFileName(String iconFileName) {
        this.iconFileName = iconFileName;
    }

    @Override
    public String getContentAsJson() {
        return contentAsJson;
    }

    @Override
    public void setContentAsJson(String contentAsJson) {
        this.contentAsJson = contentAsJson;
    }

}

/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.core.domain.model.impl;

import com.abixen.platform.core.domain.model.SecurableModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "layout")
@SequenceGenerator(sequenceName = "layout_seq", name = "layout_seq", allocationSize = 1)
public class Layout extends AuditingModel implements SecurableModel {

    public static final int LAYOUT_TITLE_MAX_LENGTH = 40;
    public static final int LAYOUT_CONTENT_MAX_LENGTH = 4000;
    public static final int LAYOUT_ICON_FILE_NAME_MAX_LENGTH = 100;

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconFileName() {
        return iconFileName;
    }

    public void setIconFileName(String iconFileName) {
        this.iconFileName = iconFileName;
    }

    public String getContentAsJson() {
        return contentAsJson;
    }

    public void setContentAsJson(String contentAsJson) {
        this.contentAsJson = contentAsJson;
    }

}

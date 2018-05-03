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

package com.abixen.platform.service.webcontent.domain.model;

import com.abixen.platform.common.domain.model.audit.SimpleAuditingModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "web_content")
@SequenceGenerator(sequenceName = "web_content_seq", name = "web_content_seq", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
public class WebContent extends SimpleAuditingModel implements Serializable {

    private static final long serialVersionUID = 994392175080662107L;

    public static final int TYPE_MAX_LENGTH = 255;
    public static final int TITLE_MAX_LENGTH = 255;
    public static final int CONTENT_MAX_LENGTH = 1000000;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "web_content_seq", strategy = GenerationType.SEQUENCE)
    protected Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = TYPE_MAX_LENGTH)
    protected WebContentType type;

    @Column(name = "title", nullable = false, length = TITLE_MAX_LENGTH)
    protected String title;

    @Column(name = "content", nullable = false, length = CONTENT_MAX_LENGTH)
    protected String content;

    @Override
    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public WebContentType getType() {
        return type;
    }

    protected void setType(WebContentType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    protected void setContent(String content) {
        this.content = content;
    }

    public void changeTitle(String title) {
        setTitle(title);
    }

    public void changeContent(String content) {
        setContent(content);
    }
}
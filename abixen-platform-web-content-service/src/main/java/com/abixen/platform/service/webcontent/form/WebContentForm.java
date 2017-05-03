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

package com.abixen.platform.service.webcontent.form;

import com.abixen.platform.common.form.Form;
import com.abixen.platform.service.webcontent.dto.WebContentDto;
import com.abixen.platform.service.webcontent.model.enumtype.WebContentType;
import com.abixen.platform.service.webcontent.model.impl.WebContent;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "classType")
@JsonSubTypes({@JsonSubTypes.Type(value = SimpleWebContentForm.class, name = "SIMPLE"),
        @JsonSubTypes.Type(value = AdvancedWebContentForm.class, name = "ADVANCED")})
public class WebContentForm implements Form {

    protected Long id;

    @NotNull
    protected WebContentType type;

    @NotNull
    @Size(max = WebContent.TITLE_MAX_LENGTH)
    protected String title;

    @NotNull
    @Size(max = WebContent.CONTENT_MAX_LENGTH)
    protected String content;

    public WebContentForm() {
    }

    public WebContentForm(WebContentDto webContent) {
        this.id = webContent.getId();
        this.type = webContent.getType();
        this.title = webContent.getTitle();
        this.content = webContent.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WebContentType getType() {
        return type;
    }

    public void setType(WebContentType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
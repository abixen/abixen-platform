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

import com.abixen.platform.core.form.Form;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.service.webcontent.model.impl.WebContent;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;

public class WebContentForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    protected Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    protected String title;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    protected String content;

    public WebContentForm() {
    }

    public WebContentForm(WebContent webContent) {
        this.id = webContent.getId();
        this.title = webContent.getTitle();
        this.content = webContent.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

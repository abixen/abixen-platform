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
import com.abixen.platform.service.webcontent.model.impl.Template;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TemplateForm implements Form {

    private Long id;

    @NotNull
    @Size(min = Template.NAME_MIN_LENGTH, max = Template.NAME_MAX_LENGTH)
    private String name;

    @NotNull
    @Size(max = Template.CONTENT_MAX_LENGTH)
    private String content;

    public TemplateForm() {
    }

    public TemplateForm(Template template) {
        this.id = template.getId();
        this.content = template.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
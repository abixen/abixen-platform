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
import com.abixen.platform.service.webcontent.model.impl.Structure;
import com.abixen.platform.service.webcontent.model.web.TemplateWeb;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StructureForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    private Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    @Size(min = Structure.NAME_MIN_LENGTH, max = Structure.NAME_MAX_LENGTH)
    private String name;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    @Size(max = Structure.CONTENT_MAX_LENGTH)
    private String content;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private TemplateWeb template;

    public StructureForm() {
    }

    public StructureForm(Structure structure) {
        this.id = structure.getId();
        this.name = structure.getName();
        this.content = structure.getContent();
        this.template = structure.getTemplate();
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

    public TemplateWeb getTemplate() {
        return template;
    }

    public void setTemplate(TemplateWeb template) {
        this.template = template;
    }
}

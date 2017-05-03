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
import com.abixen.platform.service.webcontent.dto.StructureDto;
import com.abixen.platform.service.webcontent.dto.TemplateDto;
import com.abixen.platform.service.webcontent.model.impl.Structure;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StructureForm implements Form {

    private Long id;

    @NotNull
    @Size(min = Structure.NAME_MIN_LENGTH, max = Structure.NAME_MAX_LENGTH)
    private String name;

    @NotNull
    @Size(max = Structure.CONTENT_MAX_LENGTH)
    private String content;

    @NotNull
    private TemplateDto template;

    public StructureForm() {
    }

    public StructureForm(StructureDto structure) {
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

    public TemplateDto getTemplate() {
        return template;
    }

    public void setTemplate(TemplateDto template) {
        this.template = template;
    }
}
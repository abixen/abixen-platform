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

package com.abixen.platform.core.form;

import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.model.web.LayoutWeb;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;


public class PageForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    private Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private String title;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private String icon;

    @JsonView(WebModelJsonSerialize.class)
    private String description;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private LayoutWeb layout;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public PageForm() {
    }

    public PageForm(Page page) {
        this.layout = page.getLayout();
        this.id = page.getId();
        this.title = page.getTitle();
        this.icon = page.getIcon();
        this.description = page.getDescription();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LayoutWeb getLayout() {
        return layout;
    }

    public void setLayout(LayoutWeb layout) {
        this.layout = layout;
    }


}

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

import com.abixen.platform.core.model.web.CommentWeb;
import com.abixen.platform.core.model.web.ModuleWeb;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    private Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private String message;

    @JsonView(WebModelJsonSerialize.class)
    private CommentWeb parent;

    @JsonView(WebModelJsonSerialize.class)
    private ModuleWeb module;


    public CommentForm() {
    }

    public CommentForm(CommentWeb commentWeb) {

        this.id = commentWeb.getId();
        this.message = commentWeb.getMessage();
        this.parent = commentWeb.getParent();
        this.module = commentWeb.getModule();
    }
}

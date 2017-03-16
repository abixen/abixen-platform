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

package com.abixen.platform.core.dto;

import com.abixen.platform.core.model.impl.Comment;
import com.abixen.platform.core.model.web.UserWeb;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ModuleCommentDto {
    @JsonView(WebModelJsonSerialize.class)
    private Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private String message;

    @JsonView(WebModelJsonSerialize.class)
    private Long parentId;

    @JsonView(WebModelJsonSerialize.class)
    private Long moduleId;

    @JsonView(WebModelJsonSerialize.class)
    private UserWeb user;

    @JsonView(WebModelJsonSerialize.class)
    private List<ModuleCommentDto> children;

    @JsonView(WebModelJsonSerialize.class)
    private Date createdDate;

    @JsonView(WebModelJsonSerialize.class)
    private Integer depth;


    public ModuleCommentDto() {
    }

    public ModuleCommentDto(Comment comment) {

        this.id = comment.getId();
        this.message = comment.getMessage();
        this.moduleId = comment.getModule().getId();
        this.user = comment.getCreatedBy();
        this.createdDate = comment.getCreatedDate();
        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        } else {
            this.parentId = 0L; //cannot use null here
        }
    }

}
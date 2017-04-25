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

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class
ModuleCommentDto {
    private Long id;

    @NotNull
    private String message;

    private Long parentId;

    private Long moduleId;

    private UserDto user;

    private List<ModuleCommentDto> children;

    private List<CommentVoteDto> voteDtos;

    private Date createdDate;

    private Integer depth;


    public ModuleCommentDto() {
    }

    public ModuleCommentDto(CommentDto comment) {

        this.id = comment.getId();
        this.message = comment.getMessage();
        this.moduleId = comment.getModule().getId();
        this.user = comment.getCreatedBy();
        this.voteDtos = comment.getVotes();
        this.createdDate = comment.getCreatedDate();
        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        } else {
            this.parentId = 0L; //cannot use null here
        }
    }

}
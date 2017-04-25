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

import com.abixen.platform.common.form.Form;
import com.abixen.platform.core.dto.CommentDto;
import com.abixen.platform.core.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.abixen.platform.common.model.CommentBase.COMMENT_MESSAGE_MAX_LENGTH;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentForm implements Form {

    private Long id;

    @NotNull
    @Length(max = COMMENT_MESSAGE_MAX_LENGTH)
    private String message;

    private Long parentId;

    @NotNull
    private Long moduleId;

    private UserDto user;

    private Date createdDate;

    public CommentForm() {
    }

    public CommentForm(CommentDto comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        }
        if (comment.getModule() != null) {
            this.moduleId = comment.getModule().getId();
        }
        this.user = comment.getCreatedBy();
        this.createdDate = comment.getCreatedDate();
    }
}

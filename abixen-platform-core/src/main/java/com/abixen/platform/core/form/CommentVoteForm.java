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

import com.abixen.platform.core.dto.CommentDto;
import com.abixen.platform.core.dto.CommentVoteDto;
import com.abixen.platform.core.model.enumtype.CommentVoteType;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentVoteForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    private Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private CommentVoteType commentVoteType;

    @JsonView(WebModelJsonSerialize.class)
    private CommentDto comment;

    public CommentVoteForm() {
    }

    public CommentVoteForm(CommentVoteDto commentVote) {
        this.id = commentVote.getId();
        this.comment = commentVote.getComment();
        this.commentVoteType = commentVote.getCommentVoteType();
    }

}
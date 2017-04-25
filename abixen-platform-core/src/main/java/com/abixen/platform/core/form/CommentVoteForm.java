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
import com.abixen.platform.common.model.enumtype.CommentVoteType;
import com.abixen.platform.core.dto.CommentVoteDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentVoteForm implements Form {

    private Long id;

    @NotNull
    private CommentVoteType commentVoteType;

    private Long commentId;

    public CommentVoteForm() {
    }

    public CommentVoteForm(CommentVoteDto commentVote) {
        this.id = commentVote.getId();
        this.commentId = commentVote.getCommentId();
        this.commentVoteType = commentVote.getCommentVoteType();
    }

}
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

package com.abixen.platform.core.application.service;

import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.core.application.converter.CommentVoteToCommentVoteDtoConverter;
import com.abixen.platform.core.application.dto.CommentVoteDto;
import com.abixen.platform.core.application.form.CommentVoteForm;
import com.abixen.platform.core.domain.model.Comment;
import com.abixen.platform.core.domain.model.CommentVote;
import com.abixen.platform.core.domain.service.CommentService;
import com.abixen.platform.core.domain.service.CommentVoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@PlatformApplicationService
public class CommentVoteManagementService {

    private final CommentVoteService commentVoteService;
    private final CommentService commentService;
    private final CommentVoteToCommentVoteDtoConverter commentVoteToCommentVoteDtoConverter;

    @Autowired
    public CommentVoteManagementService(CommentVoteService commentVoteService,
                                        CommentService commentService,
                                        CommentVoteToCommentVoteDtoConverter commentVoteToCommentVoteDtoConverter) {
        this.commentVoteService = commentVoteService;
        this.commentService = commentService;
        this.commentVoteToCommentVoteDtoConverter = commentVoteToCommentVoteDtoConverter;
    }

    public CommentVoteForm createCommentVote(final CommentVoteForm commentVoteForm) {
        log.debug("createCommentVote() - commentVoteForm: {}", commentVoteForm);

        final Comment comment = commentService.find(commentVoteForm.getCommentId());
        final CommentVote commentVote = CommentVote.builder()
                .type(commentVoteForm.getCommentVoteType())
                .comment(comment)
                .build();

        final CommentVote createdCommentVote = commentVoteService.create(commentVote);
        final CommentVoteDto createdCommentVoteDto = commentVoteToCommentVoteDtoConverter.convert(createdCommentVote);

        return new CommentVoteForm(createdCommentVoteDto);
    }

    public void deleteCommentVote(final Long id) {
        log.debug("deleteCommentVote() - id: {}", id);

        commentVoteService.delete(id);
    }

}
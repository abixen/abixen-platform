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

package com.abixen.platform.core.domain.service;

import com.abixen.platform.common.infrastructure.annotation.PlatformDomainService;
import com.abixen.platform.core.domain.model.CommentVote;
import com.abixen.platform.core.domain.repository.CommentVoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@PlatformDomainService
public class CommentVoteService {

    private final CommentVoteRepository commentVoteRepository;

    @Autowired
    public CommentVoteService(CommentVoteRepository commentVoteRepository) {
        this.commentVoteRepository = commentVoteRepository;
    }

    public CommentVote create(final CommentVote commentVote) {
        log.debug("create() - commentVote: {}", commentVote);

        return commentVoteRepository.save(commentVote);
    }

    public void delete(final Long id) {
        log.debug("delete() - id: {}", id);

        commentVoteRepository.delete(id);
    }

    public void deleteAllByCommentIds(final List<Long> commentIds) {
        log.debug("deleteAllByCommentIds() - commentIds: {}", commentIds);

        if (!commentIds.isEmpty()) {
            commentVoteRepository.deleteAll(commentIds);
        }
    }

}
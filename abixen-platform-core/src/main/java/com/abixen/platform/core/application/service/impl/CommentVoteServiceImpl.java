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

package com.abixen.platform.core.application.service.impl;

import com.abixen.platform.core.application.dto.CommentVoteDto;
import com.abixen.platform.core.application.form.CommentVoteForm;
import com.abixen.platform.core.application.service.CommentVoteService;
import com.abixen.platform.core.domain.model.CommentVote;
import com.abixen.platform.core.domain.model.CommentVoteBuilder;
import com.abixen.platform.core.domain.repository.CommentRepository;
import com.abixen.platform.core.domain.repository.CommentVoteRepository;
import com.abixen.platform.core.interfaces.web.facade.converter.CommentVoteToCommentVoteDtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class CommentVoteServiceImpl implements CommentVoteService {

    private final CommentVoteRepository commentVoteRepository;
    private final CommentRepository commentRepository;
    private final CommentVoteToCommentVoteDtoConverter commentVoteToCommentVoteDtoConverter;

    @Autowired
    public CommentVoteServiceImpl(CommentVoteRepository commentVoteRepository,
                                  CommentRepository commentRepository,
                                  CommentVoteToCommentVoteDtoConverter commentVoteToCommentVoteDtoConverter) {
        this.commentVoteRepository = commentVoteRepository;
        this.commentRepository = commentRepository;
        this.commentVoteToCommentVoteDtoConverter = commentVoteToCommentVoteDtoConverter;
    }

    @Override
    public CommentVoteDto saveCommentVote(CommentVoteForm commentVoteForm) {
        log.debug("saveCommentVote() - commentVoteForm={}", commentVoteForm);
        Long commentId = commentVoteForm.getCommentId();
        //FIXME - impossible id commentId == null
        CommentVote commentVote = new CommentVoteBuilder()
                .type(commentVoteForm.getCommentVoteType())
                .comment(commentId != null ? commentRepository.findOne(commentId) : null)
                .build();

        CommentVote savedCommentVote = commentVoteRepository.save(commentVote);

        CommentVoteDto savedCommentVoteDto = commentVoteToCommentVoteDtoConverter.convert(savedCommentVote);
        return savedCommentVoteDto;
    }

    @Override
    public CommentVoteForm updateCommentVote(CommentVoteForm commentVoteForm) {
        log.debug("saveCommentVote() - commentVoteForm={}", commentVoteForm);
        CommentVote commentVote = commentVoteRepository.findOne(commentVoteForm.getId());
        commentVote.changeType(commentVoteForm.getCommentVoteType());
        //FIXME
        CommentVote updatedComment = commentVoteRepository.save(commentVote);
        return commentVoteForm;
    }

    @Override
    public void deleteById(Long voteId) {
        log.debug("deleteById() - voteId = {}", voteId);
        commentVoteRepository.delete(voteId);
        return;
    }

    @Override
    public void deleteByCommentIds(List<Long> commentIds) {
        log.debug("deleteCommentVoteByCommentId() - commentIds = {}", commentIds);
        if (!commentIds.isEmpty()) {
            commentVoteRepository.deleteByCommentId(commentIds);
        }
    }

    @Override
    public List<CommentVoteDto> findVotes(Long commentId) {
        List<CommentVote> votes = commentVoteRepository.findVotesForComment(commentId);
        List<CommentVoteDto> votesDto = votes.stream().map(commentVoteToCommentVoteDtoConverter::convert).collect(Collectors.toList());
        return votesDto;
    }
}
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

package com.abixen.platform.core.service.impl;

import com.abixen.platform.core.form.CommentForm;
import com.abixen.platform.core.model.impl.Comment;
import com.abixen.platform.core.repository.CommentRepository;
import com.abixen.platform.core.repository.CommentVoteRepository;
import com.abixen.platform.core.repository.ModuleRepository;
import com.abixen.platform.core.repository.UserRepository;
import com.abixen.platform.core.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final CommentVoteRepository commentVoteRepository;


    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModuleRepository moduleRepository, UserRepository userRepository, CommentVoteRepository commentVoteRepository) {
        this.commentRepository = commentRepository;
        this.moduleRepository = moduleRepository;
        this.userRepository = userRepository;
        this.commentVoteRepository = commentVoteRepository;
    }

    @Override
    public CommentForm saveComment(CommentForm commentForm) {
        log.debug("saveComment() - commentForm={}", commentForm);

        Comment comment = buildComment(commentForm);
        Comment savedComment = commentRepository.save(comment);
        return new CommentForm(savedComment);
    }

    @Override
    public List<Comment> getAllComments(Long moduleId) {
        log.debug("getAllComments() - moduleId={}", moduleId);

        return commentRepository.getAllComments(moduleId);
    }

    @Override
    public void deleteComments(Long moduleId) {
        log.debug("deleteCommentsForModule() - moduleId = {}", moduleId);
        List<Comment> comments = this.commentRepository.getAllComments(moduleId);
        comments.forEach(comment -> commentVoteRepository.deleteCommentVotes(comment.getId()));
        commentRepository.deleteComments(moduleId);
    }

    private Comment buildComment(CommentForm commentForm) {
        Comment comment = new Comment();
        comment.setId(commentForm.getId());
        comment.setMessage(commentForm.getMessage());
        Long parentCommentId = commentForm.getParentId();
        comment.setParent(parentCommentId != null ? commentRepository.findOne(parentCommentId) : null);
        Long moduleId = commentForm.getModuleId();
        comment.setModule(moduleId != null ? moduleRepository.findOne(moduleId) : null);
        return comment;
    }
}
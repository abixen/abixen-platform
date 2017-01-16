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
import com.abixen.platform.core.model.web.CommentWeb;
import com.abixen.platform.core.repository.CommentRepository;
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

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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

    private Comment buildComment(CommentForm commentForm) {
        Comment comment = new Comment();
        comment.setId(commentForm.getId());
        comment.setMessage(commentForm.getMessage());
        CommentWeb parentComment = commentForm.getParent();
        Long parentCommentId = parentComment != null ? parentComment.getId() : null;
        comment.setParent(parentCommentId != null ? commentRepository.findOne(parentCommentId) : null);

        return comment;
    }
}
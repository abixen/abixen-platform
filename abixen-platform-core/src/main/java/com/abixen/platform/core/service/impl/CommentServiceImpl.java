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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Override
    public Comment saveComment(Comment comment) {
        log.debug("saveComment() - comment: " + comment);

        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

    @Override
    public List<Comment> getAllCommentsByModuleId(Long moduleId) {
        log.debug("getAllCommentsByModuleId() - moduleId: " + moduleId);

        List<Comment> allCommentsForTheModuleId = commentRepository.findAllComment(moduleId);
        return allCommentsForTheModuleId;
    }

    @Override
    public Comment buildComment(CommentForm commentForm) {
        log.debug("buildComment() - commentForm: " + commentForm);

        Comment comment = new Comment();
        comment.setId(commentForm.getId());
        comment.setMessage(commentForm.getMessage());
        CommentWeb parentComment  = commentForm.getParent();
        Long parentCommentId =  parentComment != null ? parentComment.getId() : null;
        comment.setParent(parentCommentId != null ? commentRepository.findOne(parentCommentId) : null);

        return comment;
    }
}
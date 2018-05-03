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
import com.abixen.platform.core.domain.model.Comment;
import com.abixen.platform.core.domain.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@PlatformDomainService
public class CommentService {

    private final CommentVoteService commentVoteService;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentVoteService commentVoteService,
                          CommentRepository commentRepository) {
        this.commentVoteService = commentVoteService;
        this.commentRepository = commentRepository;
    }


    public Comment find(Long id) {
        log.debug("find() - id: {}", id);

        return commentRepository.findOne(id);
    }

    public List<Comment> findAllByModuleId(final Long moduleId) {
        log.debug("findAll() - findAllByModuleId: {}", moduleId);

        return commentRepository.findAllByModuleId(moduleId);
    }

    public List<Comment> findAllByParentId(final Long parentId) {
        log.debug("findAllByParentId() - parentId: {}", parentId);

        return commentRepository.findAllByParentId(parentId);
    }

    public Comment create(final Comment comment) {
        log.debug("create() - comment: {}", comment);

        return commentRepository.save(comment);
    }

    public Comment update(final Comment comment) {
        log.debug("update() - comment: {}", comment);

        return commentRepository.save(comment);
    }

    public void delete(final Long id) {
        log.debug("delete() - id: {}", id);

        final Comment rootComment = find(id);

        final List<Comment> allUnderlying = findAllUnderlying(rootComment, new ArrayList<>());

        final List<Long> allUnderlyingCommentIds = allUnderlying
                .stream()
                .map(comment -> comment.getId())
                .collect(Collectors.toList());

        commentVoteService.deleteAllByCommentIds(allUnderlyingCommentIds);

        commentRepository.delete(rootComment);
        allUnderlying.forEach(commentRepository::delete);

        log.info("Removed comment {} and {} underlying comments.", id, allUnderlying.size());
    }

    public void deleteAllByModuleIds(final List<Long> moduleIds) {
        log.debug("deleteAllByModuleIds() - moduleIds: {}", moduleIds);

        moduleIds.forEach(moduleId -> {
            final List<Comment> comments = findAllByModuleId(moduleId);
            final List<Long> commentIds = comments
                    .stream()
                    .map(comment -> comment.getId())
                    .collect(Collectors.toList());

            commentVoteService.deleteAllByCommentIds(commentIds);
        });

        commentRepository.deleteAllByModuleIds(moduleIds);
    }

    private List<Comment> findAllUnderlying(Comment rootComment, ArrayList<Comment> accumulator) {
        final List<Comment> children = findAllByParentId(rootComment.getId());

        if (CollectionUtils.isEmpty(children)) {
            return accumulator;
        } else {
            accumulator.addAll(children);
            children.forEach(child -> findAllUnderlying(child, accumulator));
        }

        return accumulator;
    }

}
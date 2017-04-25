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

import com.abixen.platform.core.converter.CommentToCommentDtoConverter;
import com.abixen.platform.core.dto.CommentDto;
import com.abixen.platform.core.dto.ModuleCommentDto;
import com.abixen.platform.core.form.CommentForm;
import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.Comment;
import com.abixen.platform.core.repository.CommentRepository;
import com.abixen.platform.core.repository.ModuleRepository;
import com.abixen.platform.core.repository.UserRepository;
import com.abixen.platform.core.service.CommentService;
import com.abixen.platform.core.service.CommentVoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final CommentVoteService commentVoteService;
    private final CommentToCommentDtoConverter commentToCommentDtoConverter;


    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              ModuleRepository moduleRepository,
                              UserRepository userRepository,
                              CommentVoteService commentVoteService,
                              CommentToCommentDtoConverter commentToCommentDtoConverter) {
        this.commentRepository = commentRepository;
        this.moduleRepository = moduleRepository;
        this.userRepository = userRepository;
        this.commentVoteService = commentVoteService;
        this.commentToCommentDtoConverter = commentToCommentDtoConverter;
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_COMMENT_ADD + "')")
    public CommentForm createComment(CommentForm commentForm) {
        log.debug("createComment() - commentForm={}", commentForm);

        Comment comment = buildComment(commentForm);
        Comment savedComment = commentRepository.save(comment);
        CommentDto savedCommentDto = commentToCommentDtoConverter.convert(savedComment);

        return new CommentForm(savedCommentDto);
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_COMMENT_EDIT + "')")
    public CommentForm updateComment(CommentForm commentForm) {
        log.debug("updateComment() - commentForm={}", commentForm);

        Comment comment = buildComment(commentForm);
        Comment savedComment = commentRepository.save(comment);
        CommentDto savedCommentDto = commentToCommentDtoConverter.convert(savedComment);

        return new CommentForm(savedCommentDto);
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_COMMENT_VIEW + "')")
    public List<Comment> getAllComments(Long moduleId) {
        log.debug("getAllComments() - moduleId={}", moduleId);

        return commentRepository.getAllComments(moduleId);
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_COMMENT_VIEW + "')")
    public List<ModuleCommentDto> findComments(Long moduleId) {
        List<Comment> comments = getAllComments(moduleId);
        List<CommentDto> commentDtos = commentToCommentDtoConverter.convertToList(comments);
        populateCommentsWithVotes(commentDtos);
        List<ModuleCommentDto> moduleCommentDtos = commentDtos.stream().map(ModuleCommentDto::new).collect(toList());
        Map<Long, List<ModuleCommentDto>> groupByParent = moduleCommentDtos.stream().collect(Collectors.groupingBy(ModuleCommentDto::getParentId));
        List<ModuleCommentDto> rootComments = groupByParent.get(0L);
        if (rootComments != null) {
            populateChildren(rootComments, groupByParent);
            calculateMaxDepth(rootComments, 0);
            return rootComments;
        } else {
            return Collections.emptyList();
        }
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_COMMENT_DELETE + "')")
    public Integer deleteComment(Long commentId) {
        Comment rootNode = commentRepository.findOne(commentId);
        List<Comment> allUnderlying = findAllUnderlying(rootNode, new ArrayList<>());
        allUnderlying.add(rootNode);
        allUnderlying.forEach(commentRepository::delete);
        return allUnderlying.size();
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_COMMENT_DELETE + "')")
    public void deleteCommentByModuleIds(List<Long> moduleIds) {
        log.debug("deleteCommentByModuleId() - moduleIds : {}", moduleIds);

        moduleIds.forEach(moduleId -> {
            List<Comment> comments = this.commentRepository.getAllComments(moduleId);
            List<Long> commentIds = comments.stream().map(comment -> comment.getId()).collect(Collectors.toList());
            commentVoteService.deleteByCommentIds(commentIds);
        });
        commentRepository.deleteCommentsByModuleIds(moduleIds);
    }

    private List<Comment> findAllUnderlying(Comment rootNode, ArrayList<Comment> accumulator) {
        List<Comment> children = commentRepository.getCommentsWithParent(rootNode.getId());
        if (CollectionUtils.isEmpty(children)) {
            return accumulator;
        } else {
            accumulator.addAll(children);
            children.forEach(child -> findAllUnderlying(child, accumulator));
        }
        return accumulator;
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

    private Integer calculateMaxDepth(List<ModuleCommentDto> rootComments, Integer depth) {
        int thisLevelMax = depth;
        depth++;
        for (ModuleCommentDto dto : rootComments) {
            if (!CollectionUtils.isEmpty(dto.getChildren())) {
                int curRes = calculateMaxDepth(dto.getChildren(), depth);
                if (curRes > thisLevelMax) {
                    thisLevelMax = curRes;
                }
            } else {
                if (depth > thisLevelMax) {
                    thisLevelMax = depth;
                }
            }
            dto.setDepth(thisLevelMax);
        }
        return thisLevelMax;
    }

    private void populateChildren(List<ModuleCommentDto> rootComments, Map<Long, List<ModuleCommentDto>> groupByParent) {
        for (ModuleCommentDto dto : rootComments) {
            dto.setChildren(groupByParent.get(dto.getId()));
            if (!CollectionUtils.isEmpty(dto.getChildren())) {
                populateChildren(dto.getChildren(), groupByParent);
            }
        }
    }

    private void populateCommentsWithVotes(List<CommentDto> comments) {
        comments.forEach(comment ->
            comment.setVotes(commentVoteService.findVotes(comment.getId()))
        );
    }
}
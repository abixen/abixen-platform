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

import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.core.application.converter.CommentToCommentDtoConverter;
import com.abixen.platform.core.application.dto.CommentDto;
import com.abixen.platform.core.application.dto.ModuleCommentDto;
import com.abixen.platform.core.application.form.CommentForm;
import com.abixen.platform.core.domain.model.Comment;
import com.abixen.platform.core.domain.service.CommentService;
import com.abixen.platform.core.domain.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@Transactional
@PlatformApplicationService
public class CommentManagementService {

    private final CommentService commentService;
    private final ModuleService moduleService;
    private final CommentToCommentDtoConverter commentToCommentDtoConverter;


    @Autowired
    public CommentManagementService(CommentService commentService,
                                    ModuleService moduleService,
                                    CommentToCommentDtoConverter commentToCommentDtoConverter) {
        this.commentService = commentService;
        this.moduleService = moduleService;
        this.commentToCommentDtoConverter = commentToCommentDtoConverter;
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_COMMENT_VIEW + "')")
    public List<ModuleCommentDto> findAllComments(final Long moduleId) {
        final List<Comment> comments = commentService.findAllByModuleId(moduleId);
        final List<CommentDto> commentDtos = commentToCommentDtoConverter.convertToList(comments);
        final List<ModuleCommentDto> moduleCommentDtos = commentDtos.stream().map(ModuleCommentDto::new).collect(toList());
        final Map<Long, List<ModuleCommentDto>> groupByParent = moduleCommentDtos.stream().collect(Collectors.groupingBy(ModuleCommentDto::getParentId));
        final List<ModuleCommentDto> rootComments = groupByParent.get(0L);

        if (rootComments != null) {
            populateChildren(rootComments, groupByParent);
            calculateMaxDepth(rootComments, 0);

            return rootComments;
        } else {
            return Collections.emptyList();
        }
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_COMMENT_ADD + "')")
    public CommentForm createComment(CommentForm commentForm) {
        log.debug("createComment() - commentForm: {}", commentForm);

        final Comment comment = Comment.builder()
                .message(commentForm.getMessage())
                .parent(commentForm.getParentId() == null ? null : commentService.find(commentForm.getParentId()))
                .module(commentForm.getModuleId() == null ? null : moduleService.find(commentForm.getModuleId()))
                .build();

        final Comment createdComment = commentService.create(comment);
        final CommentDto createdCommentDto = commentToCommentDtoConverter.convert(createdComment);

        return new CommentForm(createdCommentDto);
    }

    @PreAuthorize("hasPermission('" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_COMMENT_EDIT + "')")
    public CommentForm updateComment(CommentForm commentForm) {
        log.debug("updateComment() - commentForm: {}", commentForm);

        final Comment comment = commentService.find(commentForm.getId());
        comment.changeMessage(commentForm.getMessage());

        final Comment updatedComment = commentService.update(comment);
        final CommentDto savedCommentDto = commentToCommentDtoConverter.convert(updatedComment);

        return new CommentForm(savedCommentDto);
    }


    @PreAuthorize("hasPermission('" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_COMMENT_DELETE + "')")
    public void deleteComment(final Long id) {
        log.debug("deleteComment() - id: {}", id);

        commentService.delete(id);
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

}
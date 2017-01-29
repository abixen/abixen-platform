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

package com.abixen.platform.core.controller;


import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.dto.ModuleCommentDto;
import com.abixen.platform.core.form.CommentForm;
import com.abixen.platform.core.model.impl.Comment;
import com.abixen.platform.core.service.CommentService;
import com.abixen.platform.core.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping(value = "/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ModuleCommentDto> findComments(@RequestParam(value = "moduleId") Long moduleId) {
        List<Comment> comments = commentService.getAllComments(moduleId);
        List<ModuleCommentDto> commentsDto = comments.stream().map(ModuleCommentDto::new).collect(toList());
        Map<Long, List<ModuleCommentDto>> groupByParent = commentsDto.stream().collect(Collectors.groupingBy(ModuleCommentDto::getParentId));
        List<ModuleCommentDto> rootComments = groupByParent.get(0L);
        if (rootComments != null) {
            populateChildren(rootComments, groupByParent);
            calculateMaxDepth(rootComments, 0);
            return rootComments;
        } else {
            return Collections.emptyList();
        }
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createComment(@RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(commentForm, formErrors);
        }
        CommentForm savedForm = commentService.saveComment(commentForm);
        return new FormValidationResultDto(savedForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateComment(@RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(commentForm, formErrors);
        }
        CommentForm updatedForm = commentService.saveComment(commentForm);
        return new FormValidationResultDto(updatedForm);
    }


}

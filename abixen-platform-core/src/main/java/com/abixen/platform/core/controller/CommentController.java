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


import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.core.dto.ModuleCommentDto;
import com.abixen.platform.core.form.CommentForm;
import com.abixen.platform.core.service.CommentService;
import com.abixen.platform.common.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ModuleCommentDto> findComments(@RequestParam(value = "moduleId") Long moduleId) {
        return commentService.findComments(moduleId);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createComment(@RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(commentForm, formErrors);
        }
        CommentForm savedForm = commentService.createComment(commentForm);
        return new FormValidationResultDto(savedForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateComment(@RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(commentForm, formErrors);
        }
        CommentForm updatedForm = commentService.updateComment(commentForm);
        return new FormValidationResultDto(updatedForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Integer deleteComment(@PathVariable("id") Long id) {
        return commentService.deleteComment(id);
    }

}

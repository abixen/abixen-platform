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
import com.abixen.platform.core.form.CommentForm;
import com.abixen.platform.core.model.impl.Comment;
import com.abixen.platform.core.service.CommentService;
import com.abixen.platform.core.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<CommentForm> findComments(@RequestParam(value = "moduleId") Long moduleId) {
        List<Comment> comments = commentService.getAllComments(moduleId);
        return comments.stream().map(CommentForm::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public FormValidationResultDto createComment(@RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(commentForm, formErrors);
        }
        CommentForm savedForm =  commentService.saveComment(commentForm);
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

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

package com.abixen.platform.core.interfaces.web.application;


import com.abixen.platform.common.application.dto.FormErrorDto;
import com.abixen.platform.common.application.dto.FormValidationResultDto;
import com.abixen.platform.common.infrastructure.util.ValidationUtil;
import com.abixen.platform.core.application.dto.ModuleCommentDto;
import com.abixen.platform.core.application.form.CommentForm;
import com.abixen.platform.core.application.service.CommentManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/comments")
public class CommentController {

    private CommentManagementService commentManagementService;

    @Autowired
    public CommentController(CommentManagementService commentManagementService) {
        this.commentManagementService = commentManagementService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ModuleCommentDto> findAll(@RequestParam(value = "moduleId") Long moduleId) {
        return commentManagementService.findAllComments(moduleId);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto<CommentForm> createComment(@RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(commentForm, formErrors);
        }

        final CommentForm createdCommentForm = commentManagementService.createComment(commentForm);

        return new FormValidationResultDto<>(createdCommentForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto<CommentForm> updateComment(@RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto<>(commentForm, formErrors);
        }

        final CommentForm updatedCommentForm = commentManagementService.updateComment(commentForm);

        return new FormValidationResultDto<>(updatedCommentForm);
    }

    //@PreAuthorize("hasPermission(#id, 'com.abixen.platform.core.domain.model.impl.Module', 'PAGE_DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
        log.debug("delete() - id: {}", id);
        commentManagementService.deleteComment(id);

        return new ResponseEntity(Boolean.TRUE, HttpStatus.OK);
    }

}
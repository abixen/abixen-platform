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
import com.abixen.platform.core.application.form.CommentVoteForm;
import com.abixen.platform.core.application.service.CommentVoteManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/comment-votes")
public class CommentVoteController {

    private final CommentVoteManagementService commentVoteManagementService;

    @Autowired
    public CommentVoteController(CommentVoteManagementService commentVoteManagementService) {
        this.commentVoteManagementService = commentVoteManagementService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto<CommentVoteForm> createCommentVote(@RequestBody @Valid CommentVoteForm commentVoteForm, BindingResult bindingResult) {
        log.debug("createCommentVote() - commentVoteForm: {}", commentVoteForm);

        if (bindingResult.hasErrors()) {
            final List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);

            return new FormValidationResultDto<>(commentVoteForm, formErrors);
        }
        final CommentVoteForm createdForm = commentVoteManagementService.createCommentVote(commentVoteForm);

        return new FormValidationResultDto<>(createdForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        log.debug("delete() - id: {}", id);

        commentVoteManagementService.deleteCommentVote(id);
    }

}
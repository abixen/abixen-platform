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
import com.abixen.platform.core.dto.CommentVoteDto;
import com.abixen.platform.core.form.CommentVoteForm;
import com.abixen.platform.core.service.CommentVoteService;
import com.abixen.platform.common.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/comment-votes")
public class CommentVoteController {

    @Autowired
    private CommentVoteService commentVoteService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public CommentVoteDto createCommentVote(@RequestBody @Valid CommentVoteForm commentVoteForm) {
        CommentVoteDto savedVoteDto = commentVoteService.saveCommentVote(commentVoteForm);
        return savedVoteDto;
    }

    //FixME not sure we really need this method
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public FormValidationResultDto updateCommentVote(@RequestBody @Valid CommentVoteForm commentVoteForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(commentVoteForm, formErrors);
        }
        CommentVoteForm updatedForm = commentVoteService.updateCommentVote(commentVoteForm);
        return new FormValidationResultDto(updatedForm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCommentVote(@PathVariable("id") Long id) {
        commentVoteService.deleteById(id);
        return;
    }

}

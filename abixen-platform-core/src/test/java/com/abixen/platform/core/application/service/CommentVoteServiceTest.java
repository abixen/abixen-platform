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

import com.abixen.platform.common.domain.model.enumtype.CommentVoteType;
import com.abixen.platform.core.application.dto.CommentDto;
import com.abixen.platform.core.application.dto.CommentVoteDto;
import com.abixen.platform.core.application.form.CommentForm;
import com.abixen.platform.core.application.form.CommentVoteForm;
import com.abixen.platform.core.domain.repository.CommentRepository;
import com.abixen.platform.core.domain.repository.CommentVoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@Transactional
public class CommentVoteServiceTest {

    /*@Autowired
    private CommentManagementService commentService;

    @Autowired
    private CommentVoteManagementService commentVoteService;

    @Autowired
    private CommentVoteRepository commentVoteRepository;

    @Autowired
    private CommentRepository commentRepository;

    private CommentVoteDto commentVoteDtoSaved;

    private CommentForm commentFromDB;

    @Before
    public void setUp() {
        log.debug("Test - saveFirstComment()");
        CommentForm commentForm = new CommentForm();
        commentForm.setMessage("Test Comment parent");
        commentForm.setParentId(null);
        commentFromDB = commentService.createComment(commentForm);

    }

    @After
    public void tearDown() {
        commentRepository.delete(commentFromDB.getId());
        commentVoteRepository.delete(commentVoteDtoSaved.getId());
    }

    @Ignore
    @Test
    public void saveCommentVote() {
        CommentVoteForm commentVoteForm = new CommentVoteForm();
        CommentDto comment = new CommentDto();
        comment.setId(commentFromDB.getId());
        comment.setMessage(commentFromDB.getMessage());
        commentVoteForm.setCommentId(comment.getId());
        commentVoteForm.setCommentVoteType(CommentVoteType.POSITIVE);

        commentVoteDtoSaved = commentVoteService.saveCommentVote(commentVoteForm);
        assertNotNull(commentVoteDtoSaved.getId());
        assertEquals(commentVoteDtoSaved.getCommentVoteType(), CommentVoteType.POSITIVE);
    }*/
}
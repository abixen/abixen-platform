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

package com.abixen.platform.core.service;

import com.abixen.platform.core.configuration.PlatformConfiguration;
import com.abixen.platform.core.form.CommentForm;
import com.abixen.platform.core.model.impl.Comment;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformConfiguration.class)
@Slf4j
@Transactional
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void saveComment() {

        log.debug("Test - saveFirstComment()");
        CommentForm commentForm = new CommentForm();
        commentForm.setId(1L);
        commentForm.setMessage("Test Comment parent");
        commentForm.setParentId(null);

        CommentForm commentFromDB = commentService.saveComment(commentForm);

        assertNotNull(commentFromDB);
        assertEquals(commentForm.getId(), commentFromDB.getId());
        assertEquals(commentForm.getMessage(), commentFromDB.getMessage());
        assertEquals(commentForm.getParentId(), commentFromDB.getParentId());

        CommentForm commentChild = new CommentForm();
        commentChild.setId(2L);
        commentChild.setMessage("Test Comment child");
        Comment parentComment = new Comment();
        parentComment.setId(commentFromDB.getId());

        commentChild.setParentId(parentComment.getId());
        CommentForm childCommentFromDB = commentService.saveComment(commentChild);

        assertNotNull(childCommentFromDB);
        assertEquals(childCommentFromDB.getParentId(), commentChild.getParentId());

    }
}
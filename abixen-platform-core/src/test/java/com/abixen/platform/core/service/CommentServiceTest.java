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
    public void runAllCommentServiceTests() {
        //Need to keep the order
        buildComment();
        saveComment();
    }

    public void  buildComment() {

        log.debug("Test - buildComment()");

        CommentForm commentForm = new CommentForm();
        commentForm.setId(1L);
        commentForm.setMessage("Test Comment Parent");
        commentForm.setParent(null);
        Comment commentFromBuild = commentService.buildComment(commentForm);
        Comment commentFromDB  = commentService.saveComment(commentService.buildComment(commentForm));

        assertNotNull(commentFromBuild);
        assertEquals(commentForm.getId(), commentFromBuild.getId());
        assertEquals(commentForm.getMessage(), commentFromBuild.getMessage());
        assertEquals(commentForm.getParent(), commentFromBuild.getParent());

        CommentForm commentChild = new CommentForm();
        commentChild.setId(2L);
        commentChild.setMessage("Test Comment child");
        Comment parentComment = new Comment();
        parentComment.setId(commentFromDB.getId());

        commentChild.setParent(parentComment);
        Comment childCommentFromBuild  = commentService.saveComment(commentService.buildComment(commentChild));

        assertNotNull(childCommentFromBuild);
        assertEquals(childCommentFromBuild.getParent().getId(), commentChild.getParent().getId());

    }

    public void saveComment() {

        log.debug("Test - saveFirstComment()");
        CommentForm commentForm = new CommentForm();
        commentForm.setId(1L);
        commentForm.setMessage("Test Comment parent");
        commentForm.setParent(null);

        Comment commentFromDB  = commentService.saveComment(commentService.buildComment(commentForm));

        assertNotNull(commentFromDB);
        assertEquals(commentForm.getId(), commentFromDB.getId());
        assertEquals(commentForm.getMessage(), commentFromDB.getMessage());
        assertEquals(commentForm.getParent(), commentFromDB.getParent());

        CommentForm commentChild = new CommentForm();
        commentChild.setId(2L);
        commentChild.setMessage("Test Comment child");
        Comment parentComment = new Comment();
        parentComment.setId(commentFromDB.getId());

        commentChild.setParent(parentComment);
        Comment childCommentFromDB  = commentService.saveComment(commentService.buildComment(commentChild));

        assertNotNull(childCommentFromDB);
        assertEquals(childCommentFromDB.getParent().getId(), commentChild.getParent().getId());

    }
}


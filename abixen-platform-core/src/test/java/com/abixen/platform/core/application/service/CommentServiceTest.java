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

import com.abixen.platform.core.domain.model.Comment;
import com.abixen.platform.core.domain.model.Layout;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.ModuleType;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.infrastructure.configuration.PlatformConfiguration;
import com.abixen.platform.core.application.form.CommentForm;
import com.abixen.platform.core.domain.repository.CommentRepository;
import com.abixen.platform.core.domain.repository.ModuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@Transactional
public class CommentServiceTest {

    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ModuleService moduleService;

    private Module moduleDb;

    /*@Before
    public void createModule() {

        Layout layout = new Layout();
        layout.setId(1L);
        layout.setTitle("Layout Title");
        layout.setContent("This is layout content");
        layout.setIconFileName("LayoutIconFileName");

        Page page = new Page();
        page.setId(1L);
        page.setTitle("page Title");
        page.setDescription("page Description");
        page.setLayout(layout);

        ModuleType moduleType = new ModuleType();
        moduleType.setId(1L);
        moduleType.setName("moduleType Name");
        moduleType.setTitle("moduleType Title");
        moduleType.setDescription("moduleType description");
        moduleType.setInitUrl("moduleType initUrl");
        moduleType.setServiceId("serviceId");

        Module module = new Module();
        module.setTitle("module title");
        module.setDescription("module description");
        module.setModuleType(moduleType);
        module.setPage(page);
        module.setRowIndex(1);
        module.setColumnIndex(1);
        module.setOrderIndex(1);
        moduleDb = moduleService.createModule(module);
    }*/

    //FIXME - adjust test to the new code
    @Ignore
    @Test
    public void saveComment() {

        log.debug("Test- save first comment");
        CommentForm commentParent = new CommentForm();
        commentParent.setId(1L);
        commentParent.setMessage("Test comment parent");
        commentParent.setParentId(null);
        commentParent.setModuleId(moduleDb.getId());
        CommentForm commentParentDb = commentService.createComment(commentParent);

        assertNotNull(commentParentDb);
        assertEquals(commentParent.getId(), commentParentDb.getId());
        assertEquals(commentParent.getMessage(), commentParentDb.getMessage());
        assertEquals(commentParent.getModuleId(), commentParentDb.getModuleId());

        CommentForm commentChild = new CommentForm();
        commentChild.setId(2L);
        commentChild.setMessage("Test comment child");
        commentChild.setParentId(commentParentDb.getId());
        commentChild.setModuleId(moduleDb.getId());
        CommentForm childCommentFormDb = commentService.createComment(commentChild);

        assertNotNull(childCommentFormDb);
        assertEquals(commentChild.getParentId(), childCommentFormDb.getParentId());
        assertEquals(commentChild.getModuleId(), childCommentFormDb.getModuleId());
    }

    //FIXME - adjust test to the new code
    @Ignore
    @Test
    public void getAllComments() {

        log.debug("Test- save first comment");

        CommentForm parentForm = new CommentForm();
        parentForm.setId(1L);
        parentForm.setMessage("I am parent comment");
        parentForm.setParentId(null);
        parentForm.setModuleId(moduleDb.getId());
        CommentForm parentFormDb = commentService.createComment(parentForm);

        CommentForm commentForm1 = new CommentForm();
        commentForm1.setId(2L);
        commentForm1.setMessage("Test commentForm1");
        commentForm1.setParentId(parentFormDb.getId());
        commentForm1.setModuleId(moduleDb.getId());
        commentService.createComment(commentForm1);

        CommentForm commentForm2 = new CommentForm();
        commentForm2.setId(3L);
        commentForm2.setMessage("This is commentForm2");
        commentForm2.setParentId(parentFormDb.getId());
        commentForm2.setModuleId(moduleDb.getId());
        commentService.createComment(commentForm2);

        List<CommentForm> commentFormList = new ArrayList<>();
        commentFormList.add(parentForm);
        commentFormList.add(commentForm1);
        commentFormList.add(commentForm2);

        List<Comment> commentListDb = commentService.getAllComments(moduleDb.getId());
        commentListDb.sort((commentFirst, commentSecond) -> commentFirst.getId().compareTo(commentSecond.getId()));

        CommentForm commentFormParentTest = commentFormList.get(0);
        Comment commentDbParentTest = commentListDb.get(0);
        Module moduleTest = moduleRepository.findOne(commentFormParentTest.getModuleId());
        Comment commentTest = commentRepository.findOne(parentFormDb.getId());

        assertNotNull(commentListDb);
        assertEquals(commentFormList.size(), commentListDb.size());
        assertEquals(commentFormParentTest.getId(), commentDbParentTest.getId());
        assertEquals(commentFormParentTest.getMessage(), commentDbParentTest.getMessage());
        assertEquals(commentFormParentTest.getModuleId(), commentDbParentTest.getModule().getId());
        assertEquals(moduleTest.getTitle(), commentDbParentTest.getModule().getTitle());
        assertEquals(moduleTest.getDescription(), commentDbParentTest.getModule().getDescription());

        for (int i = 1; i < commentListDb.size(); i++) {

            CommentForm commentFormTest = commentFormList.get(i);
            Comment commentDb = commentListDb.get(i);

            assertEquals(commentFormTest.getMessage(), commentDb.getMessage());
            assertEquals(commentTest.getMessage(), commentDb.getParent().getMessage());
            assertEquals(commentFormTest.getParentId(), commentDb.getParent().getId());
            assertEquals(commentFormTest.getModuleId(), commentDb.getModule().getId());
            assertEquals(moduleTest.getTitle(), commentDb.getModule().getTitle());
            assertEquals(moduleTest.getDescription(), commentDb.getModule().getDescription());
        }
    }

    //FIXME - adjust test to the new code
    @Ignore
    @Test
    public void testDeleteComment() {
        CommentForm parentForm = CommentForm.builder().message("I am parent comment").moduleId(moduleDb.getId()).build();
        CommentForm parentFormDb = commentService.createComment(parentForm);
        CommentForm commentForm1 = CommentForm.builder().message("Test commentForm1").moduleId(moduleDb.getId()).parentId(parentFormDb.getId()).build();
        commentService.createComment(commentForm1);
        CommentForm commentForm2 = CommentForm.builder().message("Test commentForm2").moduleId(moduleDb.getId()).parentId(parentFormDb.getId()).build();
        commentService.createComment(commentForm2);

        Integer numOfDeleted = commentService.deleteComment(parentFormDb.getId());
        assertEquals(new Integer(3), numOfDeleted);
    }

}
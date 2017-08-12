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
import com.abixen.platform.core.infrastructure.configuration.PlatformConfiguration;
import com.abixen.platform.core.application.dto.ModuleCommentDto;
import com.abixen.platform.core.application.form.CommentForm;
import com.abixen.platform.core.domain.model.impl.Comment;
import com.abixen.platform.core.domain.model.impl.Module;
import com.abixen.platform.core.interfaces.web.CommentController;
import com.abixen.platform.core.application.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformConfiguration.class)
public class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    CommentService commentService;

    @InjectMocks
    CommentController controller;

    List<Comment> inputComments = new ArrayList<>();
    List<ModuleCommentDto> inputModuleCommentDtos = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        Module module = new Module();
        module.setId(3L);
        Comment comment1 = Comment.builder().id(1L).message("Test Comment Parent").parent(null).module(module).build();
        Comment comment2 = Comment.builder().id(2L).message("Some new 11").parent(comment1).module(module).build();
        Comment comment3 = Comment.builder().id(3L).message("Some new 11").parent(comment2).module(module).build();
        Comment comment4 = Comment.builder().id(4L).message("Some new 22").parent(comment3).module(module).build();
        Comment comment5 = Comment.builder().id(5L).message("Some new 33").parent(comment2).module(module).build();

        inputComments.addAll(Arrays.asList(new Comment[]{comment1, comment2, comment3, comment4, comment5}));
    }

    //FIXME - this test needs some adjustment to the changes have been done
    @Test
    public void testFindCommentsForModule() throws Exception{
        when(commentService.findComments(10L)).thenReturn(inputModuleCommentDtos);

        MvcResult commentsResponse = this.mockMvc.perform(get("/api/comments/")
                .param("moduleId", "10")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();

        String contentString = commentsResponse.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<ModuleCommentDto> resList = mapper.readValue(contentString, mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, ModuleCommentDto.class));

        assertEquals(0, resList.size());
    }

    @Test
    public void testCreateComment() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CommentForm savedComment = new CommentForm();
        savedComment.setId(10L);
        savedComment.setMessage("Test Comment Parent");

        CommentForm inputComment = new CommentForm();
        inputComment.setMessage("Test Comment Parent");

        when(commentService.createComment(any(CommentForm.class))).thenReturn(savedComment);

        MvcResult commentsResponse = this.mockMvc.perform(post("/api/comments/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(inputComment))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        JSONObject jsonObject = new JSONObject(commentsResponse.getResponse().getContentAsString());

        CommentForm resForm = mapper.readValue(jsonObject.get("form").toString(), CommentForm.class);
        List<FormErrorDto> validErrors = mapper.readValue(jsonObject.get("formErrors").toString(),
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, FormErrorDto.class));
        assertNotNull(resForm);
        assertFalse(validErrors.isEmpty());
        assertTrue(resForm.getId() == null);
    }

    @Test
    public void testUpdateComment() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CommentForm updatedComment = new CommentForm();
        updatedComment.setId(10L);
        updatedComment.setMessage("Test Another Text");

        CommentForm inputComment = new CommentForm();
        inputComment.setId(10L);
        inputComment.setMessage("Test Text");

        when(commentService.updateComment(any(CommentForm.class))).thenReturn(updatedComment);

        MvcResult commentsResponse = this.mockMvc.perform(put("/api/comments/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(inputComment))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        JSONObject jsonObject = new JSONObject(commentsResponse.getResponse().getContentAsString());

        CommentForm resForm = mapper.readValue(jsonObject.get("form").toString(), CommentForm.class);
        List<FormErrorDto> validErrors = mapper.readValue(jsonObject.get("formErrors").toString(),
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, FormErrorDto.class));
        assertNotNull(resForm);
        assertFalse(validErrors.isEmpty());
        assertEquals(resForm.getMessage(), "Test Text");
    }

    @Test
    public void testDeleteComment() throws Exception {
        when(commentService.deleteComment(555L)).thenReturn(4);
        MvcResult commentsResponse = this.mockMvc.perform(delete("/api/comments/555")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        String strResp = commentsResponse.getResponse().getContentAsString();
        assertNotNull(strResp);
        assertEquals("4", strResp);

    }
}
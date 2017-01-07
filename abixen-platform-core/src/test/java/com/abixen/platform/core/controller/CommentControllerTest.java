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

import com.abixen.platform.core.configuration.PlatformConfiguration;
import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.form.CommentForm;
import com.abixen.platform.core.model.impl.Comment;
import com.abixen.platform.core.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PlatformConfiguration.class)
public class CommentControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Mock
    CommentService commentService;

    @InjectMocks
    CommentController controller;

    List<Comment> inputComments = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setMessage("Test Comment Parent");
        comment1.setParent(null);
        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setMessage("Test Comment Child");
        comment2.setParent(comment1);

        inputComments.add(comment1);
        inputComments.add(comment2);
    }

    @Test
    public void testFindCommentsForModule() throws Exception{
        when(commentService.getAllComments(10L)).thenReturn(inputComments);

        MvcResult commentsResponse = this.mockMvc.perform(get("/api/comments/")
                .param("moduleId", "10")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();

        String contentString = commentsResponse.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<CommentForm> resList = mapper.readValue(contentString, mapper.getTypeFactory().constructCollectionType(
                ArrayList.class, CommentForm.class));

        assertTrue(resList.size() == 2);
        assertTrue(resList.get(0).getId() == 1L);
    }

    @Test
    public void testCreateComment() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CommentForm savedComment = new CommentForm();
        savedComment.setId(10L);
        savedComment.setMessage("Test Comment Parent");

        CommentForm inputComment = new CommentForm();
        inputComment.setMessage("Test Comment Parent");

        when(commentService.saveComment(any(CommentForm.class))).thenReturn(savedComment);

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
        assertTrue(validErrors.isEmpty());
        assertTrue(resForm.getId() == 10);
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

        when(commentService.saveComment(any(CommentForm.class))).thenReturn(updatedComment);

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
        assertTrue(validErrors.isEmpty());
        assertEquals(resForm.getMessage(), "Test Another Text");
    }
}

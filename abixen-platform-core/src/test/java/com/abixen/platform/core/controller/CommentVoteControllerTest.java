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

import com.abixen.platform.common.application.dto.FormErrorDto;
import com.abixen.platform.common.model.enumtype.CommentVoteType;
import com.abixen.platform.core.application.dto.CommentDto;
import com.abixen.platform.core.application.dto.CommentVoteDto;
import com.abixen.platform.core.application.form.CommentVoteForm;
import com.abixen.platform.core.application.service.CommentVoteService;
import com.abixen.platform.core.interfaces.web.CommentVoteController;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentVoteControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Mock
    private CommentVoteService commentVoteService;

    @InjectMocks
    private CommentVoteController controller;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void testCreateCommentVote() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CommentDto savedComment = new CommentDto();
        savedComment.setId(10L);
        savedComment.setMessage("Test Comment Parent");
        CommentVoteDto savedCommentVoteDto = new CommentVoteDto();
        savedCommentVoteDto.setCommentId(savedComment.getId());
        savedCommentVoteDto.setCommentVoteType(CommentVoteType.POSITIVE);
        savedCommentVoteDto.setId(10L);

        CommentDto inputComment = new CommentDto();
        inputComment.setMessage("Test Comment Parent");
        CommentVoteForm inputCommentVoteForm = new CommentVoteForm();
        inputCommentVoteForm.setCommentId(inputComment.getId());
        inputCommentVoteForm.setCommentVoteType(CommentVoteType.POSITIVE);

        when(commentVoteService.saveCommentVote(any(CommentVoteForm.class))).thenReturn(savedCommentVoteDto);

        MvcResult commentsResponse = this.mockMvc.perform(post("/api/comment-votes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(inputCommentVoteForm))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        JSONObject jsonObject = new JSONObject(commentsResponse.getResponse().getContentAsString());

        CommentVoteDto resForm = mapper.readValue(jsonObject.toString(), CommentVoteDto.class);
        assertNotNull(resForm);
        assertTrue(resForm.getId() == 10);
    }

    @Test
    public void testUpdateCommentVote() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CommentDto savedComment = new CommentDto();
        savedComment.setId(10L);
        savedComment.setMessage("Test Comment Parent");
        CommentVoteForm savedCommentVoteForm = new CommentVoteForm();
        savedCommentVoteForm.setCommentId(savedComment.getId());
        savedCommentVoteForm.setCommentVoteType(CommentVoteType.POSITIVE);
        savedCommentVoteForm.setId(10L);
        CommentDto inputComment = new CommentDto();
        inputComment.setId(10L);
        inputComment.setMessage("Test Comment Parent");
        CommentVoteForm inputCommentVoteForm = new CommentVoteForm();
        inputCommentVoteForm.setId(10L);
        inputCommentVoteForm.setCommentId(inputComment.getId());
        inputCommentVoteForm.setCommentVoteType(CommentVoteType.NEGATIVE);

        when(commentVoteService.updateCommentVote(any(CommentVoteForm.class))).thenReturn(savedCommentVoteForm);

        MvcResult commentsResponse = this.mockMvc.perform(put("/api/comment-votes/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(inputCommentVoteForm))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        JSONObject jsonObject = new JSONObject(commentsResponse.getResponse().getContentAsString());

        CommentVoteForm resForm = mapper.readValue(jsonObject.get("form").toString(), CommentVoteForm.class);
        List<FormErrorDto> validErrors = mapper.readValue(jsonObject.get("formErrors").toString(),
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, FormErrorDto.class));
        assertNotNull(resForm);
        assertTrue(validErrors.isEmpty());
        assertTrue(resForm.getCommentVoteType().equals(CommentVoteType.POSITIVE));
    }
}

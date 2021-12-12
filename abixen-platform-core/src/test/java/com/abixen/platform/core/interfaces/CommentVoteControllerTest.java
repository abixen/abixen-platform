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

package com.abixen.platform.core.interfaces;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@Ignore
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentVoteControllerTest {

    /*@Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Mock
    private CommentVoteManagementService commentVoteManagementService;

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

        when(commentVoteManagementService.saveCommentVote(any(CommentVoteForm.class))).thenReturn(savedCommentVoteDto);

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

        when(commentVoteManagementService.updateCommentVote(any(CommentVoteForm.class))).thenReturn(savedCommentVoteForm);

        MvcResult commentsResponse = this.mockMvc.perform(put("/api/comment-votes/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(inputCommentVoteForm))
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        JSONObject jsonObject = new JSONObject(commentsResponse.getResponse().getContentAsString());

        CommentVoteForm resForm = mapper.readValue(jsonObject.get("form").toString(), CommentVoteForm.class);
        List<FormErrorRepresentation> validErrors = mapper.readValue(jsonObject.get("formErrors").toString(),
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, FormErrorRepresentation.class));
        assertNotNull(resForm);
        assertTrue(validErrors.isEmpty());
        assertTrue(resForm.getCommentVoteType().equals(CommentVoteType.POSITIVE));
    }*/
}

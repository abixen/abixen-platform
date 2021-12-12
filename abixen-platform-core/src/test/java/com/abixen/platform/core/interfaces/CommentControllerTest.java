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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


@Ignore
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentControllerTest {

    /*private MockMvc mockMvc;

    @Mock
    CommentManagementService commentService;

    @InjectMocks
    CommentController controller;

    List<Comment> inputComments = new ArrayList<>();
    List<ModuleCommentDto> inputModuleCommentDtos = new ArrayList<>();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        Module module = new ModuleBuilder().build();
        //module.setId(3L);
        Comment comment1 = new CommentBuilder().message("Test Comment Parent").parent(null).module(module).build();
        Comment comment2 = new CommentBuilder().message("Some new 11").parent(comment1).module(module).build();
        Comment comment3 = new CommentBuilder().message("Some new 11").parent(comment2).module(module).build();
        Comment comment4 = new CommentBuilder().message("Some new 22").parent(comment3).module(module).build();
        Comment comment5 = new CommentBuilder().message("Some new 33").parent(comment2).module(module).build();

        inputComments.addAll(Arrays.asList(new Comment[]{comment1, comment2, comment3, comment4, comment5}));
    }

    //FIXME - this test needs some adjustment to the changes have been done
    @Test
    public void testFindCommentsForModule() throws Exception {
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
        List<FormErrorRepresentation> validErrors = mapper.readValue(jsonObject.get("formErrors").toString(),
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, FormErrorRepresentation.class));
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
        List<FormErrorRepresentation> validErrors = mapper.readValue(jsonObject.get("formErrors").toString(),
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, FormErrorRepresentation.class));
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

    }*/
}
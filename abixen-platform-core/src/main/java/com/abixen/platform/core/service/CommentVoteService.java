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

import com.abixen.platform.core.dto.CommentVoteDto;
import com.abixen.platform.core.form.CommentVoteForm;

import java.util.List;

public interface CommentVoteService {

    CommentVoteDto saveCommentVote(CommentVoteForm commentVoteForm);

    CommentVoteForm updateCommentVote(CommentVoteForm commentVoteForm);

    void deleteByCommentIds(List<Long> commentIds);

    void deleteById(Long voteId);

    List<CommentVoteDto> findVotes(Long commentId);

}
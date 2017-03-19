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

package com.abixen.platform.core.converter;


import com.abixen.platform.common.converter.AbstractConverter;
import com.abixen.platform.core.dto.CommentVoteDto;
import com.abixen.platform.core.model.impl.CommentVote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class CommentVoteToCommentVoteDtoConverter extends AbstractConverter<CommentVote, CommentVoteDto> {

    private final CommentToCommentDtoConverter commentToCommentDtoConverter;
    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;

    @Autowired
    public CommentVoteToCommentVoteDtoConverter(CommentToCommentDtoConverter commentToCommentDtoConverter,
                                                AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.commentToCommentDtoConverter = commentToCommentDtoConverter;
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public CommentVoteDto convert(CommentVote commentVote, Map<String, Object> parameters) {
        CommentVoteDto commentVoteDto = new CommentVoteDto();

        commentVoteDto
                .setId(commentVote.getId())
                .setCommentId(commentVote.getComment().getId())
                .setCommentVoteType(commentVote.getCommentVoteType());


        auditingModelToAuditingDtoConverter.convert(commentVote, commentVoteDto);

        return commentVoteDto;
    }
}
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
import com.abixen.platform.core.dto.CommentDto;
import com.abixen.platform.core.model.impl.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class CommentToCommentDtoConverter extends AbstractConverter<Comment, CommentDto> {

    private final AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter;
    private final ModuleToModuleDtoConverter moduleToModuleDtoConverter;

    @Autowired
    public CommentToCommentDtoConverter(ModuleToModuleDtoConverter moduleToModuleDtoConverter,
                                        AuditingModelToAuditingDtoConverter auditingModelToAuditingDtoConverter) {
        this.moduleToModuleDtoConverter = moduleToModuleDtoConverter;
        this.auditingModelToAuditingDtoConverter = auditingModelToAuditingDtoConverter;
    }

    @Override
    public CommentDto convert(Comment comment, Map<String, Object> parameters) {
        if (comment == null) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto
                .setId(comment.getId())
                .setMessage(comment.getMessage())
                .setParent(convert(comment.getParent()))
                .setModule(moduleToModuleDtoConverter.convert(comment.getModule()));


        auditingModelToAuditingDtoConverter.convert(comment, commentDto);

        return commentDto;
    }
}
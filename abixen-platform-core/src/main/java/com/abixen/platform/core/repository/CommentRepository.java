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

package com.abixen.platform.core.repository;

import com.abixen.platform.core.model.impl.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("FROM Comment c WHERE c.module.id = :moduleId")
    List<Comment> getAllComments(@Param("moduleId") Long moduleId);

    @Query("FROM comment c WHERE c.module.id = :moduleId")
    void deleteComments(@Param("moduleId") Long moduleId);

    @Query("FROM Comment c WHERE c.parent.id = :parentId")
    List<Comment> getCommentsWithParent(@Param("parentId") Long parentId);

}
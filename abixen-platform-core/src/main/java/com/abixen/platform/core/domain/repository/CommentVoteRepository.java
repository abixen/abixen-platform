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

package com.abixen.platform.core.domain.repository;

import com.abixen.platform.core.domain.model.CommentVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {

    @Query("FROM CommentVote v WHERE v.comment.id = :commentId")
    List<CommentVote> findAll(@Param("commentId") Long commentId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM CommentVote cv WHERE cv.comment.id IN (:commentIds)")
    void deleteAll(@Param("commentIds") List<Long> commentIds);

}
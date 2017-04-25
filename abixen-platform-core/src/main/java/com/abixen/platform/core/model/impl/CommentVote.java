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

package com.abixen.platform.core.model.impl;

import com.abixen.platform.common.model.CommentVoteBase;
import com.abixen.platform.common.model.enumtype.CommentVoteType;

import javax.persistence.*;

@Entity
@Table(name = "comment_vote")
@SequenceGenerator(sequenceName = "comment_vote_seq", name = "comment_vote_seq", allocationSize = 1)
public class CommentVote extends AuditingModel implements CommentVoteBase<Comment> {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "comment_vote_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_vote_type", nullable = false)
    private CommentVoteType commentVoteType;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Comment getComment() {
        return comment;
    }

    @Override
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public CommentVoteType getCommentVoteType() {
        return commentVoteType;
    }

    @Override
    public void setCommentVoteType(CommentVoteType commentVoteType) {
        this.commentVoteType = commentVoteType;
    }
}
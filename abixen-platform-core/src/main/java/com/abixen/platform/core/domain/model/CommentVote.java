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

package com.abixen.platform.core.domain.model;

import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.common.domain.model.enumtype.CommentVoteType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "comment_vote")
@SequenceGenerator(sequenceName = "comment_vote_seq", name = "comment_vote_seq", allocationSize = 1)
public final class CommentVote extends AuditingModel {

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

    private CommentVote() {
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    private void setComment(Comment comment) {
        this.comment = comment;
    }

    public CommentVoteType getCommentVoteType() {
        return commentVoteType;
    }

    private void setCommentVoteType(CommentVoteType commentVoteType) {
        this.commentVoteType = commentVoteType;
    }

    public void changeType(CommentVoteType commentVoteType) {
        setCommentVoteType(commentVoteType);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<CommentVote> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new CommentVote();
        }

        public Builder type(CommentVoteType commentVoteType) {
            this.product.setCommentVoteType(commentVoteType);
            return this;
        }

        public Builder comment(Comment comment) {
            this.product.setComment(comment);
            return this;
        }
    }
}
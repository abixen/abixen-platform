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
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "comment")
@SequenceGenerator(sequenceName = "comment_seq", name = "comment_seq", allocationSize = 1)
public final class Comment extends AuditingModel {

    public static final int COMMENT_MESSAGE_MIN_LENGTH = 1;
    public static final int COMMENT_MESSAGE_MAX_LENGTH = 1000;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "comment_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "message", nullable = false)
    @Length(max = COMMENT_MESSAGE_MAX_LENGTH)
    @NotNull
    private String message;

    @JoinColumn(name = "parent_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;

    @JoinColumn(name = "module_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Module module;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentVote> votes = new HashSet<>();

    private Comment() {
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public Comment getParent() {
        return parent;
    }

    private void setParent(Comment parent) {
        this.parent = parent;
    }

    public Module getModule() {
        return module;
    }

    private void setModule(Module module) {
        this.module = module;
    }

    public void changeMessage(String message) {
        setMessage(message);
    }

    public Set<CommentVote> getVotes() {
        return votes;
    }

    public void setVotes(Set<CommentVote> votes) {
        this.votes = votes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<Comment> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new Comment();
        }

        public Builder message(String message) {
            this.product.setMessage(message);
            return this;
        }

        public Builder parent(Comment comment) {
            this.product.setParent(comment);
            return this;
        }

        public Builder module(Module module) {
            this.product.setModule(module);
            return this;
        }
    }

}
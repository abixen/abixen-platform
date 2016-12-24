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

import com.abixen.platform.core.model.CommentBase;
import com.abixen.platform.core.model.web.CommentWeb;

import javax.persistence.*;


@Entity
@Table(name = "comment")
@SequenceGenerator(sequenceName = "comment_seq", name = "comment_seq", allocationSize = 1)
public class Comment extends AuditingModel implements CommentBase<Comment>, CommentWeb {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "comment_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "message", length = COMMENT_MESSAGE_MAX_LENGTH, nullable = false)
    private String message;

    @JoinColumn(name = "parent_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Comment getParent() {
        return parent;
    }

    @Override
    public void setParent(Comment parent) {
        this.parent = parent;
    }
}

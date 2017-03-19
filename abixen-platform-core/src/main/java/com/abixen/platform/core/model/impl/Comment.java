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

import com.abixen.platform.common.model.CommentBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@SequenceGenerator(sequenceName = "comment_seq", name = "comment_seq", allocationSize = 1)
public class Comment extends AuditingModel implements CommentBase<Comment, Module> {

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

    @Override
    public Module getModule() {
        return module;
    }

    @Override
    public void setModule(Module module) {
        this.module = module;
    }
}
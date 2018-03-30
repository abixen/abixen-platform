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

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "comment")
@SequenceGenerator(sequenceName = "comment_seq", name = "comment_seq", allocationSize = 1)
public class Comment extends AuditingModel {

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

    Comment() {
    }

    @Override
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }

    public Comment getParent() {
        return parent;
    }

    void setParent(Comment parent) {
        this.parent = parent;
    }

    public Module getModule() {
        return module;
    }

    void setModule(Module module) {
        this.module = module;
    }

    public void changeMessage(String message) {
        setMessage(message);
    }
}
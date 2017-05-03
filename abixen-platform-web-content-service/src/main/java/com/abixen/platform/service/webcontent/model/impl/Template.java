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

package com.abixen.platform.service.webcontent.model.impl;

import com.abixen.platform.common.model.audit.AuditingModel;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "template")
@SequenceGenerator(sequenceName = "template_seq", name = "template_seq", allocationSize = 1)
public class Template extends AuditingModel implements Serializable {

    private static final long serialVersionUID = -8783217634723319219L;

    public static final int NAME_MIN_LENGTH = 6;
    public static final int NAME_MAX_LENGTH = 255;
    public static final int CONTENT_MAX_LENGTH = 1000000;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "template_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false, length = NAME_MAX_LENGTH)
    private String name;

    @Column(name = "content", nullable = false, length = CONTENT_MAX_LENGTH, columnDefinition = "text")
    @Lob
    private String content;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
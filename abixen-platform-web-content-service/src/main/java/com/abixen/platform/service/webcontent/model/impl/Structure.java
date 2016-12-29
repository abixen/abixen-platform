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

import com.abixen.platform.core.model.audit.AuditingModel;
import com.abixen.platform.service.webcontent.model.web.StructureWeb;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "structure")
@SequenceGenerator(sequenceName = "structure_seq", name = "structure_seq", allocationSize = 1)
public class Structure extends AuditingModel implements StructureWeb, Serializable {

    private static final long serialVersionUID = 1443021404845246701L;

    public static final int CONTENT_MAX_LENGHT = 1000000;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "structure_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "content", nullable = false, length = CONTENT_MAX_LENGHT, columnDefinition = "text")
    private String content;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

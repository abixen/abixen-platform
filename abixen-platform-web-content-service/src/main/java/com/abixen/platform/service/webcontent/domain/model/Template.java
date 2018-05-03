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

package com.abixen.platform.service.webcontent.domain.model;

import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.common.domain.model.audit.SimpleAuditingModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "template")
@SequenceGenerator(sequenceName = "template_seq", name = "template_seq", allocationSize = 1)
public final class Template extends SimpleAuditingModel implements Serializable {

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

    @Column(name = "content", nullable = false, length = CONTENT_MAX_LENGTH)
    private String content;

    private Template() {
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public void changeName(String name) {
        setName(name);
    }

    public void changeContent(String content) {
        setContent(content);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<Template> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new Template();
        }

        public Builder name(String name) {
            this.product.setName(name);
            return this;
        }

        public Builder content(String content) {
            this.product.setContent(content);
            return this;
        }

    }

}
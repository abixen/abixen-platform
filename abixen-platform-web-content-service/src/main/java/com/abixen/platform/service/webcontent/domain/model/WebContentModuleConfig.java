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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "web_content_module_config")
@SequenceGenerator(sequenceName = "web_content_module_config_seq", name = "web_content_module_config_seq", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
public final class WebContentModuleConfig extends SimpleAuditingModel implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "web_content_module_config_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "module_id", nullable = false)
    private Long moduleId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "content_id", nullable = false)
    private WebContent webContent;

    private WebContentModuleConfig() {
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    private void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public WebContent getWebContent() {
        return webContent;
    }

    private void setWebContent(WebContent webContent) {
        this.webContent = webContent;
    }

    public void changeWebContent(WebContent webContent) {
        setWebContent(webContent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<WebContentModuleConfig> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new WebContentModuleConfig();
        }

        public Builder moduleId(Long moduleId) {
            this.product.setModuleId(moduleId);
            return this;
        }

        public Builder webContent(WebContent webContent) {
            this.product.setWebContent(webContent);
            return this;
        }

    }

}
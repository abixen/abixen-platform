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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "advanced_web_content")
public final class AdvancedWebContent extends WebContent implements Serializable {

    private static final long serialVersionUID = -9126772442611909363L;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "structure_id", nullable = false)
    private Structure structure;

    private AdvancedWebContent() {
    }

    public Structure getStructure() {
        return structure;
    }

    private void setStructure(Structure structure) {
        this.structure = structure;
    }

    public void changeStructure(Structure structure) {
        setStructure(structure);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<AdvancedWebContent> {

        private Builder() {
            this.product.setType(WebContentType.ADVANCED);
        }

        @Override
        protected void initProduct() {
            this.product = new AdvancedWebContent();
        }

        public Builder title(String title) {
            this.product.setTitle(title);
            return this;
        }

        public Builder content(String content) {
            this.product.setContent(content);
            return this;
        }

        public Builder structure(Structure structure) {
            this.product.setStructure(structure);
            return this;
        }
    }

}
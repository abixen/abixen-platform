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
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "simple_web_content")
public final class SimpleWebContent extends WebContent implements Serializable {

    private static final long serialVersionUID = -5253029007016137717L;

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<SimpleWebContent> {

        private Builder() {
            this.product.setType(WebContentType.SIMPLE);
        }

        @Override
        protected void initProduct() {
            this.product = new SimpleWebContent();
        }

        public Builder title(String title) {
            this.product.setTitle(title);
            return this;
        }

        public Builder content(String content) {
            this.product.setContent(content);
            return this;
        }
    }

}
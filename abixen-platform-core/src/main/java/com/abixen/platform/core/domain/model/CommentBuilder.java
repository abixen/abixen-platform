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

import com.abixen.platform.common.domain.model.utils.EntityBuilder;


public class CommentBuilder extends EntityBuilder<Comment> {

    @Override
    protected void initProduct() {
        this.product = new Comment();
    }

    @Override
    protected Comment assembleProduct() {
        return this.product;
    }

    public CommentBuilder message(String message) {
        this.product.setMessage(message);
        return this;
    }

    public CommentBuilder parent(Comment comment) {
        this.product.setParent(comment);
        return this;
    }

    public CommentBuilder module(Module module) {
        this.product.setModule(module);
        return this;
    }
}
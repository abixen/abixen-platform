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

import com.abixen.platform.common.domain.model.EntityBuilder;


public class LayoutBuilder extends EntityBuilder<Layout> {

    @Override
    protected void initProduct() {
        this.product = new Layout();
    }

    @Override
    protected Layout assembleProduct() {
        return this.product;
    }

    public LayoutBuilder title(String title) {
        this.product.setTitle(title);
        return this;
    }

    public LayoutBuilder content(String content) {
        this.product.setContent(content);
        return this;
    }

    public LayoutBuilder iconFileName(String iconFileName) {
        this.product.setIconFileName(iconFileName);
        return this;
    }

}
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

package com.abixen.platform.core.util.impl;


import com.abixen.platform.core.model.impl.Layout;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.core.util.PageBuilder;


public class PageBuilderImpl extends EntityBuilder<Page> implements PageBuilder {

    @Override
    protected void initProduct() {
        this.product = new Page();
    }

    @Override
    protected Page assembleProduct() {
        return this.product;
    }

    public PageBuilder init(String title, Layout layout) {
        this.product.setTitle(title);
        this.product.setLayout(layout);
        return this;
    }

    public PageBuilder icon(String icon) {
        this.product.setIcon(icon);
        return this;
    }

    public PageBuilder description(String description) {
        this.product.setDescription(description);
        return this;
    }

}

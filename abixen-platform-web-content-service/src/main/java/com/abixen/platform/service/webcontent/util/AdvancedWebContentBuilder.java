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

package com.abixen.platform.service.webcontent.util;


import com.abixen.platform.common.util.EntityBuilder;
import com.abixen.platform.service.webcontent.model.enumtype.WebContentType;
import com.abixen.platform.service.webcontent.model.impl.AdvancedWebContent;
import com.abixen.platform.service.webcontent.model.impl.Structure;

public class AdvancedWebContentBuilder extends EntityBuilder<AdvancedWebContent> {

    public AdvancedWebContentBuilder() {
        this.product.setType(WebContentType.ADVANCED);
    }

    public AdvancedWebContentBuilder(AdvancedWebContent advancedWebContent) {
        this.product = advancedWebContent;
    }

    @Override
    protected void initProduct() {
        this.product = new AdvancedWebContent();
    }

    public AdvancedWebContentBuilder title(String title) {
        this.product.setTitle(title);
        return this;
    }

    public AdvancedWebContentBuilder content(String content) {
        this.product.setContent(content);
        return this;
    }

    public AdvancedWebContentBuilder structure(Structure structure) {
        this.product.setStructure(structure);
        return this;
    }
}
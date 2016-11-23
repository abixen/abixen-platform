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

package com.abixen.platform.core.model.web;

import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(as = Module.class)
public interface ModuleWeb {

    @JsonView(WebModelJsonSerialize.class)
    Long getId();

    @JsonView(WebModelJsonSerialize.class)
    String getTitle();

    @JsonView(WebModelJsonSerialize.class)
    String getDescription();

    @JsonView(WebModelJsonSerialize.class)
    ModuleTypeWeb getModuleType();

    @JsonView(WebModelJsonSerialize.class)
    PageWeb getPage();

    @JsonView(WebModelJsonSerialize.class)
    Integer getRowIndex();

    @JsonView(WebModelJsonSerialize.class)
    Integer getColumnIndex();

    @JsonView(WebModelJsonSerialize.class)
    Integer getOrderIndex();
}

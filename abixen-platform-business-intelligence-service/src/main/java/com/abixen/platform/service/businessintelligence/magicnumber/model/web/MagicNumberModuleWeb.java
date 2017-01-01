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

package com.abixen.platform.service.businessintelligence.magicnumber.model.web;

import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.magicnumber.model.enumtype.ColorCode;
import com.abixen.platform.service.businessintelligence.magicnumber.model.impl.MagicNumberModule;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(as = MagicNumberModule.class)
public interface MagicNumberModuleWeb {

    @JsonView(WebModelJsonSerialize.class)
    Long getId();

    @JsonView(WebModelJsonSerialize.class)
    Long getModuleId();

    @JsonView(WebModelJsonSerialize.class)
    Double getMagicNumberValue();

    @JsonView(WebModelJsonSerialize.class)
    String getMagicNumberDescription();

    @JsonView(WebModelJsonSerialize.class)
    Integer getMagicNumberDisplayPrecision();

    @JsonView(WebModelJsonSerialize.class)
    String getPrefixValue();

    @JsonView(WebModelJsonSerialize.class)
    String getSufixValue();

    @JsonView(WebModelJsonSerialize.class)
    String getIconClass();

    @JsonView(WebModelJsonSerialize.class)
    ColorCode getColorCode();

    @JsonView(WebModelJsonSerialize.class)
    Double getAdditionalNumberValue();

    @JsonView(WebModelJsonSerialize.class)
    Integer getAdditionalNumberDisplayPrecision();

    @JsonView(WebModelJsonSerialize.class)
    String getAdditionalNumberDescription();

    @JsonView(WebModelJsonSerialize.class)
    String getAdditionalNumberPrefixValue();

    @JsonView(WebModelJsonSerialize.class)
    String getAdditionalNumberSufixValue();

}

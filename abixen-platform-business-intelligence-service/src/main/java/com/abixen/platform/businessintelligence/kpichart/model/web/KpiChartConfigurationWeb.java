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

package com.abixen.platform.businessintelligence.kpichart.model.web;

import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.businessintelligence.kpichart.model.enumtype.AnimationType;
import com.abixen.platform.businessintelligence.kpichart.model.enumtype.ColorCode;
import com.abixen.platform.businessintelligence.kpichart.model.impl.KpiChartConfiguration;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(as = KpiChartConfiguration.class)
public interface KpiChartConfigurationWeb {

    @JsonView(WebModelJsonSerialize.class)
    Long getId();

    @JsonView(WebModelJsonSerialize.class)
    Long getModuleId();

    @JsonView(WebModelJsonSerialize.class)
    Double getValue();

    @JsonView(WebModelJsonSerialize.class)
    Double getMaxValue();

    @JsonView(WebModelJsonSerialize.class)
    Integer getLineWidth();

    @JsonView(WebModelJsonSerialize.class)
    Integer getRadius();

    @JsonView(WebModelJsonSerialize.class)
    Boolean isSemi();

    @JsonView(WebModelJsonSerialize.class)
    Boolean isClockwise();

    @JsonView(WebModelJsonSerialize.class)
    Boolean isResponsive();

    @JsonView(WebModelJsonSerialize.class)
    Boolean isRounded();

    @JsonView(WebModelJsonSerialize.class)
    Integer getAnimationDuration();

    @JsonView(WebModelJsonSerialize.class)
    Integer getAnimationDelay();

    @JsonView(WebModelJsonSerialize.class)
    String getDescription();

    @JsonView(WebModelJsonSerialize.class)
    ColorCode getColorCode();

    @JsonView(WebModelJsonSerialize.class)
    AnimationType getAnimationType();

}

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

package com.abixen.platform.service.businessintelligence.kpichart.form;

import com.abixen.platform.core.form.Form;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.kpichart.model.enumtype.AnimationType;
import com.abixen.platform.service.businessintelligence.kpichart.model.enumtype.ColorCode;
import com.abixen.platform.service.businessintelligence.kpichart.model.impl.KpiChartConfiguration;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;


public class KpiChartConfigurationForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    private Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Long moduleId;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Double value;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Double maxValue;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Integer lineWidth;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Integer radius;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Boolean semi;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Boolean clockwise;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Boolean responsive;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Boolean rounded;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Integer animationDuration;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Integer animationDelay;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private String description;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private ColorCode colorCode;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private AnimationType animationType;

    public KpiChartConfigurationForm() {
    }

    public KpiChartConfigurationForm(KpiChartConfiguration kpiChartConfiguration) {
        this.id = kpiChartConfiguration.getId();
        this.moduleId = kpiChartConfiguration.getModuleId();
        this.value = kpiChartConfiguration.getValue();
        this.maxValue = kpiChartConfiguration.getMaxValue();
        this.lineWidth = kpiChartConfiguration.getLineWidth();
        this.radius = kpiChartConfiguration.getRadius();
        this.semi = kpiChartConfiguration.isSemi();
        this.clockwise = kpiChartConfiguration.isClockwise();
        this.responsive = kpiChartConfiguration.isResponsive();
        this.rounded = kpiChartConfiguration.isRounded();
        this.animationDuration = kpiChartConfiguration.getAnimationDuration();
        this.animationDelay = kpiChartConfiguration.getAnimationDelay();
        this.animationType = kpiChartConfiguration.getAnimationType();
        this.description = kpiChartConfiguration.getDescription();
        this.colorCode = kpiChartConfiguration.getColorCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Boolean isSemi() {
        return semi;
    }

    public void setSemi(Boolean semi) {
        this.semi = semi;
    }

    public Boolean isClockwise() {
        return clockwise;
    }

    public void setClockwise(Boolean clockwise) {
        this.clockwise = clockwise;
    }

    public Boolean isResponsive() {
        return responsive;
    }

    public void setResponsive(Boolean responsive) {
        this.responsive = responsive;
    }

    public Boolean isRounded() {
        return rounded;
    }

    public void setRounded(Boolean rounded) {
        this.rounded = rounded;
    }

    public Integer getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(Integer animationDuration) {
        this.animationDuration = animationDuration;
    }

    public Integer getAnimationDelay() {
        return animationDelay;
    }

    public void setAnimationDelay(Integer animationDelay) {
        this.animationDelay = animationDelay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ColorCode getColorCode() {
        return colorCode;
    }

    public void setColorCode(ColorCode colorCode) {
        this.colorCode = colorCode;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

}

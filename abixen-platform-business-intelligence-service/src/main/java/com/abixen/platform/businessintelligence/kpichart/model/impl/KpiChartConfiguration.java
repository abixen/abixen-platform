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

package com.abixen.platform.businessintelligence.kpichart.model.impl;

import com.abixen.platform.businessintelligence.model.impl.AuditingModel;
import com.abixen.platform.businessintelligence.kpichart.model.enumtype.AnimationType;
import com.abixen.platform.businessintelligence.kpichart.model.enumtype.ColorCode;
import com.abixen.platform.businessintelligence.kpichart.model.web.KpiChartConfigurationWeb;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "kpi_chart_configuration")
@SequenceGenerator(sequenceName = "kpi_chart_configuration_seq", name = "kpi_chart_configuration_seq", allocationSize = 1)
public class KpiChartConfiguration extends AuditingModel implements KpiChartConfigurationWeb, Serializable {

    private static final long serialVersionUID = -1420930478759410093L;

    private static final int DESCRIPTION_MAX_LENGHT = 200;
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "kpi_chart_configuration_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "module_id", nullable = false, unique = true)
    private Long moduleId;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "max_value", nullable = false)
    private Double maxValue;

    @Column(name = "line_width", nullable = false)
    private Integer lineWidth;

    @Column(name = "radius", nullable = false)
    private Integer radius;

    @Column(name = "semi", nullable = false)
    private Boolean semi;

    @Column(name = "clockwise", nullable = false)
    private Boolean clockwise;

    @Column(name = "responsive", nullable = false)
    private Boolean responsive;

    @Column(name = "rounded", nullable = false)
    private Boolean rounded;

    @Column(name = "animation_duration", nullable = false)
    private Integer animationDuration;

    @Column(name = "animation_delay", nullable = false)
    private Integer animationDelay;

    @Column(name = "description", length = DESCRIPTION_MAX_LENGHT, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "color_code", nullable = false)
    private ColorCode colorCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "animation_type", nullable = false)
    private AnimationType animationType;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public Integer getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
    }

    @Override
    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    @Override
    public Boolean isSemi() {
        return semi;
    }

    public void setSemi(Boolean semi) {
        this.semi = semi;
    }

    @Override
    public Boolean isClockwise() {
        return clockwise;
    }

    public void setClockwise(Boolean clockwise) {
        this.clockwise = clockwise;
    }

    @Override
    public Boolean isResponsive() {
        return responsive;
    }

    public void setResponsive(Boolean responsive) {
        this.responsive = responsive;
    }

    @Override
    public Boolean isRounded() {
        return rounded;
    }

    public void setRounded(Boolean rounded) {
        this.rounded = rounded;
    }

    @Override
    public Integer getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(Integer animationDuration) {
        this.animationDuration = animationDuration;
    }

    @Override
    public Integer getAnimationDelay() {
        return animationDelay;
    }

    public void setAnimationDelay(Integer animationDelay) {
        this.animationDelay = animationDelay;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public ColorCode getColorCode() {
        return colorCode;
    }

    public void setColorCode(ColorCode colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public AnimationType getAnimationType() {
        return animationType;
    }

    public void setAnimationType(AnimationType animationType) {
        this.animationType = animationType;
    }

}

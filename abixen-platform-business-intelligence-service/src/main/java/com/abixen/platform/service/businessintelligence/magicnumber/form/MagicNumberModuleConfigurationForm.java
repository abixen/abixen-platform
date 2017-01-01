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

package com.abixen.platform.service.businessintelligence.magicnumber.form;

import com.abixen.platform.core.form.Form;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.magicnumber.model.enumtype.ColorCode;
import com.abixen.platform.service.businessintelligence.magicnumber.model.impl.MagicNumberModule;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;


public class MagicNumberModuleConfigurationForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    private Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Long moduleId;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Double magicNumberValue;

    @NotNull
    @JsonView(WebModelJsonSerialize.class)
    private Integer magicNumberDisplayPrecision;

    @JsonView(WebModelJsonSerialize.class)
    private String magicNumberDescription;

    @JsonView(WebModelJsonSerialize.class)
    private String prefixValue;

    @JsonView(WebModelJsonSerialize.class)
    private String sufixValue;

    @JsonView(WebModelJsonSerialize.class)
    private Double additionalNumberValue;

    @JsonView(WebModelJsonSerialize.class)
    private Integer additionalNumberDisplayPrecision;

    @JsonView(WebModelJsonSerialize.class)
    private String additionalNumberDescription;

    @JsonView(WebModelJsonSerialize.class)
    private String additionalNumberPrefixValue;

    @JsonView(WebModelJsonSerialize.class)
    private String additionalNumberSufixValue;

    @JsonView(WebModelJsonSerialize.class)
    private String iconClass;

    @NotNull
    @JsonView(WebModelJsonSerialize.class)
    private ColorCode colorCode;

    public MagicNumberModuleConfigurationForm() {
    }

    public MagicNumberModuleConfigurationForm(MagicNumberModule magicNumberModule) {
        this.id = magicNumberModule.getId();
        this.moduleId = magicNumberModule.getModuleId();
        this.magicNumberValue = magicNumberModule.getMagicNumberValue();
        this.magicNumberDisplayPrecision = magicNumberModule.getMagicNumberDisplayPrecision();
        this.magicNumberDescription = magicNumberModule.getMagicNumberDescription();
        this.prefixValue = magicNumberModule.getPrefixValue();
        this.sufixValue = magicNumberModule.getSufixValue();
        this.iconClass = magicNumberModule.getIconClass();
        this.colorCode = magicNumberModule.getColorCode();
        this.additionalNumberValue = magicNumberModule.getAdditionalNumberValue();
        this.additionalNumberDescription = magicNumberModule.getAdditionalNumberDescription();
        this.additionalNumberDisplayPrecision = magicNumberModule.getAdditionalNumberDisplayPrecision();
        this.additionalNumberPrefixValue = magicNumberModule.getAdditionalNumberPrefixValue();
        this.additionalNumberSufixValue = magicNumberModule.getAdditionalNumberSufixValue();
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

    public Double getMagicNumberValue() {
        return magicNumberValue;
    }

    public void setMagicNumberValue(Double magicNumberValue) {
        this.magicNumberValue = magicNumberValue;
    }

    public String getMagicNumberDescription() {
        return magicNumberDescription;
    }

    public void setMagicNumberDescription(String magicNumberDescription) {
        this.magicNumberDescription = magicNumberDescription;
    }

    public Integer getMagicNumberDisplayPrecision() {
        return magicNumberDisplayPrecision;
    }

    public void setMagicNumberDisplayPrecision(Integer magicNumberDisplayPrecision) {
        this.magicNumberDisplayPrecision = magicNumberDisplayPrecision;
    }

    public String getPrefixValue() {
        return prefixValue;
    }

    public void setPrefixValue(String prefixValue) {
        this.prefixValue = prefixValue;
    }

    public String getSufixValue() {
        return sufixValue;
    }

    public void setSufixValue(String sufixValue) {
        this.sufixValue = sufixValue;
    }

    public Double getAdditionalNumberValue() {
        return additionalNumberValue;
    }

    public void setAdditionalNumberValue(Double additionalNumberValue) {
        this.additionalNumberValue = additionalNumberValue;
    }

    public Integer getAdditionalNumberDisplayPrecision() {
        return additionalNumberDisplayPrecision;
    }

    public void setAdditionalNumberDisplayPrecision(Integer additionalNumberDisplayPrecision) {
        this.additionalNumberDisplayPrecision = additionalNumberDisplayPrecision;
    }

    public String getAdditionalNumberDescription() {
        return additionalNumberDescription;
    }

    public void setAdditionalNumberDescription(String additionalNumberDescription) {
        this.additionalNumberDescription = additionalNumberDescription;
    }

    public String getAdditionalNumberPrefixValue() {
        return additionalNumberPrefixValue;
    }

    public void setAdditionalNumberPrefixValue(String additionalNumberPrefixValue) {
        this.additionalNumberPrefixValue = additionalNumberPrefixValue;
    }

    public String getAdditionalNumberSufixValue() {
        return additionalNumberSufixValue;
    }

    public void setAdditionalNumberSufixValue(String additionalNumberSufixValue) {
        this.additionalNumberSufixValue = additionalNumberSufixValue;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public ColorCode getColorCode() {
        return colorCode;
    }

    public void setColorCode(ColorCode colorCode) {
        this.colorCode = colorCode;
    }


}

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

package com.abixen.platform.module.magicnumber.model.impl;

import com.abixen.platform.module.magicnumber.model.enumtype.ColorCode;
import com.abixen.platform.module.magicnumber.model.web.MagicNumberModuleWeb;
import com.abixen.platform.module.model.impl.AuditingModel;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "magic_number_module")
@SequenceGenerator(sequenceName = "magic_number_module_seq", name = "magic_number_module_seq", allocationSize = 1)
public class MagicNumberModule extends AuditingModel implements MagicNumberModuleWeb, Serializable {

    private static final long serialVersionUID = -1420930478759410093L;

    public static final int MAGIC_NUMBER_DESCRIPTION_MAX_LENGHT = 100;
    public static final int PREFIX_VALUE_MAX_LENGHT = 10;
    public static final int SUFIX_VALUE_MAX_LENGHT = 10;
    public static final int ADDITIONAL_NUMBER_DESCRIPTION_MAX_LENGHT = 100;
    public static final int ADDITIONAL_NUMBER_PREFIX_VALUE_MAX_LENGHT = 10;
    public static final int ADDITIONAL_NUMBER_SUFIX_VALUE_MAX_LENGHT = 10;
    public static final int ICON_CLASS_MAX_LENGHT = 50;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "magic_number_module_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "module_id", nullable = false, unique = true)
    private Long moduleId;

    @Column(name = "magic_number_value", nullable = false)
    private Double magicNumberValue;

    @Column(name = "magic_number_display_precision", nullable = false)
    private Integer magicNumberDisplayPrecision;

    @Column(name = "magic_number_description", length = MAGIC_NUMBER_DESCRIPTION_MAX_LENGHT, nullable = false)
    private String magicNumberDescription;

    @Column(name = "prefix_value", length = PREFIX_VALUE_MAX_LENGHT)
    private String prefixValue;

    @Column(name = "sufix_value", length = SUFIX_VALUE_MAX_LENGHT)
    private String sufixValue;

    @Column(name = "additional_number_value")
    private Double additionalNumberValue;

    @Column(name = "additional_number_display_precision")
    private Integer additionalNumberDisplayPrecision;

    @Column(name = "additional_number_description", length = ADDITIONAL_NUMBER_DESCRIPTION_MAX_LENGHT)
    private String additionalNumberDescription;

    @Column(name = "additional_number_prefix_value", length = ADDITIONAL_NUMBER_PREFIX_VALUE_MAX_LENGHT)
    private String additionalNumberPrefixValue;

    @Column(name = "additional_number_sufix_value", length = ADDITIONAL_NUMBER_SUFIX_VALUE_MAX_LENGHT)
    private String additionalNumberSufixValue;

    @Column(name = "icon_class", length = ICON_CLASS_MAX_LENGHT, nullable = false)
    private String iconClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "color_code", nullable = false)
    private ColorCode colorCode;

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
    public Double getMagicNumberValue() {
        return magicNumberValue;
    }

    public void setMagicNumberValue(Double magicNumberValue) {
        this.magicNumberValue = magicNumberValue;
    }

    @Override
    public Integer getMagicNumberDisplayPrecision() {
        return magicNumberDisplayPrecision;
    }

    public void setMagicNumberDisplayPrecision(Integer magicNumberDisplayPrecision) {
        this.magicNumberDisplayPrecision = magicNumberDisplayPrecision;
    }

    @Override
    public String getMagicNumberDescription() {
        return magicNumberDescription;
    }

    public void setMagicNumberDescription(String magicNumberDescription) {
        this.magicNumberDescription = magicNumberDescription;
    }

    @Override
    public String getPrefixValue() {
        return prefixValue;
    }

    public void setPrefixValue(String prefixValue) {
        this.prefixValue = prefixValue;
    }

    @Override
    public String getSufixValue() {
        return sufixValue;
    }

    public void setSufixValue(String sufixValue) {
        this.sufixValue = sufixValue;
    }

    @Override
    public Double getAdditionalNumberValue() {
        return additionalNumberValue;
    }

    public void setAdditionalNumberValue(Double additionalNumberValue) {
        this.additionalNumberValue = additionalNumberValue;
    }

    @Override
    public Integer getAdditionalNumberDisplayPrecision() {
        return additionalNumberDisplayPrecision;
    }

    public void setAdditionalNumberDisplayPrecision(Integer additionalNumberDisplayPrecision) {
        this.additionalNumberDisplayPrecision = additionalNumberDisplayPrecision;
    }

    @Override
    public String getAdditionalNumberDescription() {
        return additionalNumberDescription;
    }

    public void setAdditionalNumberDescription(String additionalNumberDescription) {
        this.additionalNumberDescription = additionalNumberDescription;
    }

    @Override
    public String getAdditionalNumberPrefixValue() {
        return additionalNumberPrefixValue;
    }

    public void setAdditionalNumberPrefixValue(String additionalNumberPrefixValue) {
        this.additionalNumberPrefixValue = additionalNumberPrefixValue;
    }

    @Override
    public String getAdditionalNumberSufixValue() {
        return additionalNumberSufixValue;
    }

    public void setAdditionalNumberSufixValue(String additionalNumberSufixValue) {
        this.additionalNumberSufixValue = additionalNumberSufixValue;
    }

    @Override
    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    @Override
    public ColorCode getColorCode() {
        return colorCode;
    }

    public void setColorCode(ColorCode colorCode) {
        this.colorCode = colorCode;
    }

}

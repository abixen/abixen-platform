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

package com.abixen.platform.service.businessintelligence.magicnumber.util.impl;

import com.abixen.platform.core.util.EntityBuilder;
import com.abixen.platform.service.businessintelligence.magicnumber.model.enumtype.ColorCode;
import com.abixen.platform.service.businessintelligence.magicnumber.model.impl.MagicNumberModule;
import com.abixen.platform.service.businessintelligence.magicnumber.util.MagicNumberModuleBuilder;


public class MagicNumberModuleBuilderImpl extends EntityBuilder<MagicNumberModule> implements MagicNumberModuleBuilder {

    @Override
    public MagicNumberModuleBuilder create() {
        return this;
    }

    @Override
    public MagicNumberModuleBuilder basic(Long moduleId, ColorCode colorCode, String iconClass) {
        this.product.setModuleId(moduleId);
        this.product.setColorCode(colorCode);
        this.product.setIconClass(iconClass);
        return this;
    }

    @Override
    public MagicNumberModuleBuilder number(Double magicNumberValue, Integer magicNumberDisplayPrecision) {
        this.product.setMagicNumberValue(magicNumberValue);
        this.product.setMagicNumberDisplayPrecision(magicNumberDisplayPrecision);
        return this;
    }

    @Override
    public MagicNumberModuleBuilder numberDescription(String magicNumberDescription) {
        this.product.setMagicNumberDescription(magicNumberDescription);
        return this;
    }

    @Override
    public MagicNumberModuleBuilder numberAfix(String prefixValue, String sufixValue) {
        this.product.setPrefixValue(prefixValue);
        this.product.setSufixValue(sufixValue);
        return this;
    }

    @Override
    public MagicNumberModuleBuilder additionalNumber(Double additionalNumberValue, Integer additionalNumberDisplayPrecision) {
        this.product.setAdditionalNumberValue(additionalNumberValue);
        this.product.setAdditionalNumberDisplayPrecision(additionalNumberDisplayPrecision);
        return this;
    }

    @Override
    public MagicNumberModuleBuilder additionalNumberDescription(String additionalNumberDescription) {
        this.product.setAdditionalNumberDescription(additionalNumberDescription);
        return this;
    }

    @Override
    public MagicNumberModuleBuilder additionalNumberAfix(String additionalNumberprefixValue, String additionalNumberSufixValue) {
        this.product.setAdditionalNumberPrefixValue(additionalNumberprefixValue);
        this.product.setAdditionalNumberSufixValue(additionalNumberSufixValue);
        return this;
    }

    @Override
    protected void initProduct() {
        this.product = new MagicNumberModule();
    }

}
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

package com.abixen.platform.module.magicnumber.service.impl;

import com.abixen.platform.module.magicnumber.form.MagicNumberModuleConfigurationForm;
import com.abixen.platform.module.magicnumber.model.impl.MagicNumberModule;
import com.abixen.platform.module.magicnumber.repository.MagicNumberModuleRepository;
import com.abixen.platform.module.magicnumber.service.MagicNumberModuleDomainBuilderService;
import com.abixen.platform.module.magicnumber.service.MagicNumberModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class MagicNumberModuleServiceImpl implements MagicNumberModuleService {

    private final Logger log = LoggerFactory.getLogger(MagicNumberModuleServiceImpl.class);

    @Resource
    private MagicNumberModuleRepository magicNumberModuleRepository;

    @Autowired
    MagicNumberModuleDomainBuilderService magicNumberModuleDomainBuilderService;

    @Override
    public MagicNumberModule buildMagicNumberModule(MagicNumberModuleConfigurationForm magicNumberModuleConfigurationForm) {
        log.debug("buildMagicNumberModule() - magicNumberModuleConfigurationForm: " + magicNumberModuleConfigurationForm);
        return magicNumberModuleDomainBuilderService.newMagicNumberModuleBuilderInstance()
                .basic(magicNumberModuleConfigurationForm.getModuleId(), magicNumberModuleConfigurationForm.getColorCode(), magicNumberModuleConfigurationForm.getIconClass())
                .number(magicNumberModuleConfigurationForm.getMagicNumberValue(), magicNumberModuleConfigurationForm.getMagicNumberDisplayPrecision())
                .numberDescription(magicNumberModuleConfigurationForm.getMagicNumberDescription())
                .numberAfix(magicNumberModuleConfigurationForm.getPrefixValue(), magicNumberModuleConfigurationForm.getSufixValue())
                .additionalNumber(magicNumberModuleConfigurationForm.getAdditionalNumberValue(), magicNumberModuleConfigurationForm.getAdditionalNumberDisplayPrecision())
                .additionalNumberAfix(magicNumberModuleConfigurationForm.getAdditionalNumberPrefixValue(), magicNumberModuleConfigurationForm.getAdditionalNumberSufixValue())
                .additionalNumberDescription(magicNumberModuleConfigurationForm.getAdditionalNumberDescription())
                .build();
    }

    @Override
    public MagicNumberModuleConfigurationForm createMagicNumberModule(MagicNumberModuleConfigurationForm magicNumberModuleConfigurationForm) {
        MagicNumberModule magicNumberModule = buildMagicNumberModule(magicNumberModuleConfigurationForm);
        return new MagicNumberModuleConfigurationForm(updateMagicNumberModule(createMagicNumberModule(magicNumberModule)));
    }

    @Override
    public MagicNumberModuleConfigurationForm updateMagicNumberModule(MagicNumberModuleConfigurationForm magicNumberModuleConfigurationForm) {
        log.debug("updateMagicNumberModule() - magicNumberModuleConfigurationForm: " + magicNumberModuleConfigurationForm);

        MagicNumberModule magicNumberModule = findMagicNumberModuleByModuleId(magicNumberModuleConfigurationForm.getModuleId());
        magicNumberModule.setMagicNumberValue(magicNumberModuleConfigurationForm.getMagicNumberValue());
        magicNumberModule.setMagicNumberDisplayPrecision(magicNumberModuleConfigurationForm.getMagicNumberDisplayPrecision());
        magicNumberModule.setPrefixValue(magicNumberModuleConfigurationForm.getPrefixValue());
        magicNumberModule.setSufixValue(magicNumberModuleConfigurationForm.getSufixValue());
        magicNumberModule.setMagicNumberDescription(magicNumberModuleConfigurationForm.getMagicNumberDescription());
        magicNumberModule.setIconClass(magicNumberModuleConfigurationForm.getIconClass());
        magicNumberModule.setColorCode(magicNumberModuleConfigurationForm.getColorCode());
        magicNumberModule.setAdditionalNumberValue(magicNumberModuleConfigurationForm.getAdditionalNumberValue());
        magicNumberModule.setAdditionalNumberDisplayPrecision(magicNumberModuleConfigurationForm.getMagicNumberDisplayPrecision());
        magicNumberModule.setAdditionalNumberPrefixValue(magicNumberModuleConfigurationForm.getAdditionalNumberPrefixValue());
        magicNumberModule.setAdditionalNumberSufixValue(magicNumberModuleConfigurationForm.getAdditionalNumberSufixValue());
        magicNumberModule.setAdditionalNumberDescription(magicNumberModuleConfigurationForm.getAdditionalNumberDescription());

        return new MagicNumberModuleConfigurationForm(updateMagicNumberModule(magicNumberModule));
    }

    @Override
    public MagicNumberModule findMagicNumberModuleByModuleId(Long moduleId) {
        return magicNumberModuleRepository.findByModuleId(moduleId);
    }

    @Override
    public MagicNumberModule createMagicNumberModule(MagicNumberModule magicNumberModule) {
        log.debug("createMagicNumberModule() - magicNumberModule: " + magicNumberModule);
        MagicNumberModule createdMagicNumberModule = magicNumberModuleRepository.save(magicNumberModule);
        return createdMagicNumberModule;
    }

    @Override
    public MagicNumberModule updateMagicNumberModule(MagicNumberModule magicNumberModule) {
        log.debug("updateMagicNumberModule() - magicNumberModule: " + magicNumberModule);
        return magicNumberModuleRepository.save(magicNumberModule);
    }
}

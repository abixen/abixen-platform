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

package com.abixen.platform.module.controller;

import com.abixen.platform.module.chart.controller.ChartInitController;
import com.abixen.platform.module.configuration.properties.ModulesConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/configuration")
public class ConfigurationController {

    private final Logger log = LoggerFactory.getLogger(ChartInitController.class.getName());

    private final ModulesConfigurationProperties modulesConfigurationProperties;

    @Autowired
    public ConfigurationController(ModulesConfigurationProperties modulesConfigurationProperties) {
        this.modulesConfigurationProperties = modulesConfigurationProperties;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModulesConfigurationProperties getModulesConfigurationProperties() {
        log.debug("getModulesConfigurationProperties - modulesConfigurationProperties: " + modulesConfigurationProperties);
        return modulesConfigurationProperties;
    }

}

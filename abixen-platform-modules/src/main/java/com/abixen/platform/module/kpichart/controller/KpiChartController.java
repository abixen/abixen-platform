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

package com.abixen.platform.module.kpichart.controller;

import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.module.kpichart.model.web.KpiChartConfigurationWeb;
import com.abixen.platform.module.kpichart.service.KpiChartConfigurationService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/application/modules/abixen/kpi-chart")
public class KpiChartController {

    private final Logger log = LoggerFactory.getLogger(KpiChartController.class.getName());

    @Autowired
    KpiChartConfigurationService chartConfigurationService;

    @PreAuthorize("hasPermission(#moduleId, 'Module', 'MODULE_VIEW')")
    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/{moduleId}", method = RequestMethod.GET)
    public KpiChartConfigurationWeb getKpiChartConfiguration(@PathVariable Long moduleId) {
        log.debug("getKpiChartConfiguration() - moduleId: " + moduleId);

        return chartConfigurationService.findKpiChartConfigurationByModuleId(moduleId);
    }


}

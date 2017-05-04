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

package com.abixen.platform.service.businessintelligence.multivisualisation.controller;

import com.abixen.platform.common.model.enumtype.AclClassName;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataValueDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.ChartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/service/abixen/business-intelligence/application/multi-visualisation/data")
public class ChartDataController {

    private final ChartDataService chartDataService;

    @Autowired
    public ChartDataController(ChartDataService chartDataService) {
        this.chartDataService = chartDataService;
    }

    @PreAuthorize("hasPermission(#chartConfigurationForm.moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_VIEW + "')")
    @RequestMapping(value = "/{seriesName}", method = RequestMethod.POST)
    public List<Map<String, DataValueDto>> getDataForChart(@PathVariable String seriesName, @RequestBody @Valid ChartConfigurationForm chartConfigurationForm) {
        return chartDataService.getChartData(chartConfigurationForm, seriesName);
    }

    @PreAuthorize("hasPermission(#chartConfigurationForm.moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_VIEW + "')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public List<Map<String, DataValueDto>> getDataForChart(@RequestBody @Valid ChartConfigurationForm chartConfigurationForm) {
        return chartDataService.getChartData(chartConfigurationForm, null);
    }

}
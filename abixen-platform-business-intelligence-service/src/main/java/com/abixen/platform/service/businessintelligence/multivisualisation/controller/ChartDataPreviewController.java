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

import com.abixen.platform.core.model.enumtype.AclClassName;
import com.abixen.platform.core.model.enumtype.PermissionName;
import com.abixen.platform.service.businessintelligence.multivisualisation.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataValueWeb;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.ChartDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/service/abixen/business-intelligence/application/multi-visualization/data-preview")
public class ChartDataPreviewController {

    private final ChartDataService chartDataService;

    @Autowired
    public ChartDataPreviewController(ChartDataService chartDataService) {
        this.chartDataService = chartDataService;
    }

    //TODO - change POST to GET
    @PreAuthorize("hasPermission(#chartConfigurationForm.moduleId, '" + AclClassName.Values.MODULE + "', '" + PermissionName.Values.MODULE_VIEW + "')")
    @RequestMapping(value = "/{seriesName}", method = RequestMethod.POST)
    public List<Map<String, DataValueWeb>> getPreviewDataForChart(@RequestBody @Valid ChartConfigurationForm chartConfigurationForm, @PathVariable("seriesName") String seriesName) {
        log.debug("getPreviewDataForChart - chartConfigurationForm: " + chartConfigurationForm);
        log.debug("getPreviewDataForChart - value: " + seriesName);
        return chartDataService.getChartDataPreview(chartConfigurationForm, seriesName);
    }

}
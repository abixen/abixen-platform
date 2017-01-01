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

package com.abixen.platform.service.businessintelligence.chart.controller;

import com.abixen.platform.service.businessintelligence.chart.form.ChartConfigurationForm;
import com.abixen.platform.service.businessintelligence.chart.model.web.DataSourceValueWeb;
import com.abixen.platform.service.businessintelligence.chart.service.ChartDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/application/businessintelligence/abixen/multi-visualization/data-preview")
public class ChartDataPreviewController {

    @Autowired
    private ChartDataService chartDataService;

    @RequestMapping(value = "/{seriesName}", method = RequestMethod.POST)
    public List<Map<String, DataSourceValueWeb>> getPreviewDataForChart(@RequestBody @Valid ChartConfigurationForm chartConfigurationForm, @PathVariable("seriesName") String seriesName) {
        log.debug("getPreviewDataForChart - chartConfigurationForm: " + chartConfigurationForm);
        log.debug("getPreviewDataForChart - value: " + seriesName);
        return chartDataService.getChartDataPreview(chartConfigurationForm, seriesName);
    }


}

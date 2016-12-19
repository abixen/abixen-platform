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

package com.abixen.platform.module.chart.controller;

import com.abixen.platform.module.chart.form.ChartConfigurationForm;
import com.abixen.platform.module.chart.model.web.DataSourceValueWeb;
import com.abixen.platform.module.chart.service.ChartDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/application/modules/abixen/multi-visualization/data")
public class ChartDataController {

    @Autowired
    private ChartDataService chartDataService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public List<Map<String, DataSourceValueWeb>> getDataForChart(@RequestBody @Valid ChartConfigurationForm chartConfigurationForm) {
        log.debug("getDataForChart - chartConfigurationForm: " + chartConfigurationForm);
        return chartDataService.getChartData(chartConfigurationForm);
    }


}

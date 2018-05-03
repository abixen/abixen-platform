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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.form;

import com.abixen.platform.common.application.form.Form;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.ChartConfigurationDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSetDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.dto.DataSourceDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.ChartType;

import javax.validation.constraints.NotNull;


public class ChartConfigurationForm implements Form {

    private Long id;

    @NotNull
    private Long moduleId;

    @NotNull
    private ChartType chartType;

    @NotNull
    private DataSetDto dataSet;

    @NotNull
    private String axisXName;

    @NotNull
    private String axisYName;

    private String filter;

    @NotNull
    private DataSourceDto dataSource;

    public ChartConfigurationForm() {
    }

    public ChartConfigurationForm(ChartConfigurationDto chartConfiguration) {
        this.id = chartConfiguration.getId();
        this.moduleId = chartConfiguration.getModuleId();
        this.chartType = chartConfiguration.getChartType();
        this.dataSet = chartConfiguration.getDataSet();
        this.axisXName = chartConfiguration.getAxisXName();
        this.axisYName = chartConfiguration.getAxisYName();
        this.dataSource = chartConfiguration.getDataSource();
        this.filter = chartConfiguration.getFilter();
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

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    public DataSetDto getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSetDto dataSet) {
        this.dataSet = dataSet;
    }

    public String getAxisXName() {
        return axisXName;
    }

    public void setAxisXName(String axisXName) {
        this.axisXName = axisXName;
    }

    public String getAxisYName() {
        return axisYName;
    }

    public void setAxisYName(String axisYName) {
        this.axisYName = axisYName;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public DataSourceDto getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSourceDto dataSource) {
        this.dataSource = dataSource;
    }

}

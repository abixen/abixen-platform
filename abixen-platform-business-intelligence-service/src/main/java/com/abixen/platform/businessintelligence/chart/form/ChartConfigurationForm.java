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

package com.abixen.platform.businessintelligence.chart.form;

import com.abixen.platform.core.form.Form;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.abixen.platform.businessintelligence.chart.model.enumtype.ChartType;
import com.abixen.platform.businessintelligence.chart.model.impl.ChartConfiguration;
import com.abixen.platform.businessintelligence.chart.model.web.DataSetChartWeb;
import com.abixen.platform.businessintelligence.chart.model.web.DataSourceWeb;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;


public class ChartConfigurationForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    private Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private Long moduleId;

    @NotNull
    @JsonView(WebModelJsonSerialize.class)
    private ChartType chartType;

    @NotNull
    @JsonView(WebModelJsonSerialize.class)
    private DataSetChartWeb dataSetChart;

    @NotNull
    @JsonView(WebModelJsonSerialize.class)
    private String axisXName;

    @NotNull
    @JsonView(WebModelJsonSerialize.class)
    private String axisYName;

    @NotNull
    @JsonView(WebModelJsonSerialize.class)
    private DataSourceWeb dataSource;

    public ChartConfigurationForm() {
    }

    public ChartConfigurationForm(ChartConfiguration chartConfiguration) {
        this.id = chartConfiguration.getId();
        this.moduleId = chartConfiguration.getModuleId();
        this.chartType = chartConfiguration.getChartType();
        this.dataSetChart = chartConfiguration.getDataSetChart();
        this.axisXName = chartConfiguration.getAxisXName();
        this.axisYName = chartConfiguration.getAxisYName();
        this.dataSource = chartConfiguration.getDataSource();
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

    public DataSetChartWeb getDataSetChart() {
        return dataSetChart;
    }

    public void setDataSetChart(DataSetChartWeb dataSetChart) {
        this.dataSetChart = dataSetChart;
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

    public DataSourceWeb getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSourceWeb dataSource) {
        this.dataSource = dataSource;
    }

}

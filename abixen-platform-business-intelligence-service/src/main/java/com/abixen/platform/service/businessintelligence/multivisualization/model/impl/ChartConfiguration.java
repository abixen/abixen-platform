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

package com.abixen.platform.service.businessintelligence.multivisualization.model.impl;

import com.abixen.platform.service.businessintelligence.multivisualization.model.enumtype.ChartType;
import com.abixen.platform.service.businessintelligence.multivisualization.model.impl.datasource.DataSource;
import com.abixen.platform.service.businessintelligence.multivisualization.model.web.ChartConfigurationWeb;
import com.abixen.platform.core.model.audit.AuditingModel;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "chart_configuration")
@SequenceGenerator(sequenceName = "chart_configuration_seq", name = "chart_configuration_seq", allocationSize = 1)
public class ChartConfiguration extends AuditingModel implements ChartConfigurationWeb, Serializable {

    private static final long serialVersionUID = -1420930478759410093L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "chart_configuration_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "module_id", nullable = false, unique = true)
    private Long moduleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "chart_type", nullable = false)
    private ChartType chartType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data_set_chart_id", nullable = false)
    private DataSetChart dataSetChart;

    @Column(name = "axis_x_name", nullable = false)
    private String axisXName;

    @Column(name = "axis_y_name", nullable = false)
    private String axisYName;

    @ManyToOne
    @JoinColumn(name = "data_source_id", nullable = false)
    private DataSource dataSource;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
    }

    @Override
    public DataSetChart getDataSetChart() {
        return dataSetChart;
    }

    public void setDataSetChart(DataSetChart dataSetChart) {
        this.dataSetChart = dataSetChart;
    }

    @Override
    public String getAxisXName() {
        return axisXName;
    }

    public void setAxisXName(String axisXName) {
        this.axisXName = axisXName;
    }

    @Override
    public String getAxisYName() {
        return axisYName;
    }

    public void setAxisYName(String axisYName) {
        this.axisYName = axisYName;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


}

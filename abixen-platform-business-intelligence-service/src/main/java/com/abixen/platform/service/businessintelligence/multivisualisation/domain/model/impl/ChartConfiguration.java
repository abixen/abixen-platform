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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.ChartType;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSource;
import com.abixen.platform.common.model.audit.SimpleAuditingModel;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "chart_configuration")
@SequenceGenerator(sequenceName = "chart_configuration_seq", name = "chart_configuration_seq", allocationSize = 1)
public class ChartConfiguration extends SimpleAuditingModel implements Serializable {

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "data_set_chart_id", nullable = false)
    private DataSetChart dataSetChart;

    @Column(name = "axis_x_name")
    private String axisXName;

    @Column(name = "axis_y_name")
    private String axisYName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "filter", nullable = true)
    private String filter;

    @ManyToOne
    @JoinColumn(name = "data_source_id", nullable = false)
    private DataSource dataSource;

    ChartConfiguration() {
    }

    @Override
    public Long getId() {
        return id;
    }

    void setId(final Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    void setModuleId(final Long moduleId) {
        this.moduleId = moduleId;
    }

    public ChartType getChartType() {
        return chartType;
    }

    void setChartType(final ChartType chartType) {
        this.chartType = chartType;
    }

    public DataSetChart getDataSetChart() {
        return dataSetChart;
    }

    void setDataSetChart(final DataSetChart dataSetChart) {
        this.dataSetChart = dataSetChart;
    }

    public String getAxisXName() {
        return axisXName;
    }

    void setAxisXName(final String axisXName) {
        this.axisXName = axisXName;
    }

    public String getAxisYName() {
        return axisYName;
    }

    void setAxisYName(final String axisYName) {
        this.axisYName = axisYName;
    }

    public String getFilter() {
        return filter;
    }

    void setFilter(final String filter) {
        this.filter = filter;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void changeModuleId(final Long moduleId) {
        setModuleId(moduleId);
    }

    public void changeChartParameters(final ChartType chartType, final DataSetChart dataSetChart) {
        setChartType(chartType);
        setDataSetChart(dataSetChart);
    }

    public void changeAxisNames(final String axisXName, final String axisYName) {
        setAxisXName(axisXName);
        setAxisYName(axisYName);
    }

    public void changeDataParameters(final String filter, final DataSource dataSource) {
        setFilter(filter);
        setDataSource(dataSource);
    }


}

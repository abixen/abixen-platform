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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model;

import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.common.domain.model.audit.SimpleAuditingModel;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSource;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "chart_configuration")
@SequenceGenerator(sequenceName = "chart_configuration_seq", name = "chart_configuration_seq", allocationSize = 1)
public final class ChartConfiguration extends SimpleAuditingModel implements Serializable {

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
    @JoinColumn(name = "data_set_id", nullable = false)
    private DataSet dataSet;

    @Column(name = "axis_x_name")
    private String axisXName;

    @Column(name = "axis_y_name")
    private String axisYName;

    @Column(name = "filter")
    private String filter;

    @ManyToOne
    @JoinColumn(name = "data_source_id", nullable = false)
    private DataSource dataSource;

    private ChartConfiguration() {
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(final Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    private void setModuleId(final Long moduleId) {
        this.moduleId = moduleId;
    }

    public ChartType getChartType() {
        return chartType;
    }

    private void setChartType(final ChartType chartType) {
        this.chartType = chartType;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    private void setDataSet(final DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public String getAxisXName() {
        return axisXName;
    }

    private void setAxisXName(final String axisXName) {
        this.axisXName = axisXName;
    }

    public String getAxisYName() {
        return axisYName;
    }

    private void setAxisYName(final String axisYName) {
        this.axisYName = axisYName;
    }

    public String getFilter() {
        return filter;
    }

    private void setFilter(final String filter) {
        this.filter = filter;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    private void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void changeModuleId(final Long moduleId) {
        setModuleId(moduleId);
    }

    public void changeChartParameters(final ChartType chartType, final DataSet dataSet) {
        setChartType(chartType);
        setDataSet(dataSet);
    }

    public void changeAxisNames(final String axisXName, final String axisYName) {
        setAxisXName(axisXName);
        setAxisYName(axisYName);
    }

    public void changeDataParameters(final String filter, final DataSource dataSource) {
        setFilter(filter);
        setDataSource(dataSource);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<ChartConfiguration> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new ChartConfiguration();
        }

        public Builder moduleId(final Long moduleId) {
            this.product.setModuleId(moduleId);
            return this;
        }

        public Builder chartParameters(final ChartType chartType, final DataSet dataSet) {
            this.product.setChartType(chartType);
            this.product.setDataSet(dataSet);
            return this;
        }

        public Builder axisNames(final String axisXName, final String axisYName) {
            this.product.setAxisXName(axisXName);
            this.product.setAxisYName(axisYName);
            return this;
        }


        public Builder dataParameters(final String filter, final DataSource dataSource) {
            this.product.setFilter(filter);
            this.product.setDataSource(dataSource);
            return this;
        }
    }

}
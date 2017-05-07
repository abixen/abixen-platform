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

package com.abixen.platform.service.businessintelligence.multivisualisation.form;

import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataSetChartDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataSetSeriesColumnDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataSetSeriesDto;

import java.util.Set;

public class DataSetChartForm {

    private Long id;
    private DataSetSeriesColumnDto domainXSeriesColumn;
    private DataSetSeriesColumnDto domainZSeriesColumn;
    private Set<DataSetSeriesDto> dataSetSeries;

    public DataSetChartForm(DataSetChartDto dataSetChartDto) {
        this.id = dataSetChartDto.getId();
        this.domainXSeriesColumn = dataSetChartDto.getDomainXSeriesColumn();
        this.domainZSeriesColumn = dataSetChartDto.getDomainZSeriesColumn();
        this.dataSetSeries = dataSetChartDto.getDataSetSeries();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DataSetSeriesColumnDto getDomainXSeriesColumn() {
        return domainXSeriesColumn;
    }

    public void setDomainXSeriesColumn(DataSetSeriesColumnDto domainXSeriesColumn) {
        this.domainXSeriesColumn = domainXSeriesColumn;
    }

    public DataSetSeriesColumnDto getDomainZSeriesColumn() {
        return domainZSeriesColumn;
    }

    public void setDomainZSeriesColumn(DataSetSeriesColumnDto domainZSeriesColumn) {
        this.domainZSeriesColumn = domainZSeriesColumn;
    }

    public Set<DataSetSeriesDto> getDataSetSeries() {
        return dataSetSeries;
    }

    public void setDataSetSeries(Set<DataSetSeriesDto> dataSetSeries) {
        this.dataSetSeries = dataSetSeries;
    }
}

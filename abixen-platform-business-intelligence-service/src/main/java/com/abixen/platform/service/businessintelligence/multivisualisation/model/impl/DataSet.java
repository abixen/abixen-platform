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

package com.abixen.platform.service.businessintelligence.multivisualisation.model.impl;

import com.abixen.platform.common.model.Model;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSetWeb;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "data_set")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@SequenceGenerator(sequenceName = "data_set_seq", name = "data_set_seq", allocationSize = 1)
public class DataSet extends Model implements DataSetWeb {

    private static final long serialVersionUID = -1420930478359410091L;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "data_set_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "domain_x_series_column")
    private DataSetSeriesColumn domainXSeriesColumn;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "domain_z_series_column")
    private DataSetSeriesColumn domainZSeriesColumn;

    @OneToMany(mappedBy = "dataSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DataSetSeries> dataSetSeries = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public DataSetSeriesColumn getDomainXSeriesColumn() {
        return domainXSeriesColumn;
    }

    public void setDomainXSeriesColumn(DataSetSeriesColumn domainXSeriesColumn) {
        this.domainXSeriesColumn = domainXSeriesColumn;
    }

    @Override
    public DataSetSeriesColumn getDomainZSeriesColumn() {
        return domainZSeriesColumn;
    }

    public void setDomainZSeriesColumn(DataSetSeriesColumn domainZSeriesColumn) {
        this.domainZSeriesColumn = domainZSeriesColumn;
    }

    public Set<DataSetSeries> getDataSetSeries() {
        return dataSetSeries;
    }

    public void setDataSetSeries(Set<DataSetSeries> dataSetSeries) {
        this.dataSetSeries = dataSetSeries;
    }

}

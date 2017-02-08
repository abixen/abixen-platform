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

import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DataSetChartWeb;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "data_set_chart")
public class DataSetChart extends DataSet implements DataSetChartWeb {

    private static final long serialVersionUID = 1232457388531081321L;

    public DataSetChart() {
    }

}



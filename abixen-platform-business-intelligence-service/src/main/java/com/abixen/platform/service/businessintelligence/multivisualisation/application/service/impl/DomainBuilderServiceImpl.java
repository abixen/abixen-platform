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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.service.impl;

import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.DomainBuilderService;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.util.*;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.util.impl.*;
import org.springframework.stereotype.Service;


@Service
public class DomainBuilderServiceImpl implements DomainBuilderService {

    @Override
    public DataSetBuilder newDataSetBuilderInstance() {
        return new DataSetBuilderImpl();
    }

    @Override
    public DataSetSeriesBuilder newDataSetSeriesBuilderInstance() {
        return new DataSetSeriesBuilderImpl();
    }

    @Override
    public DataSetSeriesColumnBuilder newDataSetSeriesColumnBuilderInstance() {
        return new DataSetSeriesColumnBuilderImpl();
    }

}

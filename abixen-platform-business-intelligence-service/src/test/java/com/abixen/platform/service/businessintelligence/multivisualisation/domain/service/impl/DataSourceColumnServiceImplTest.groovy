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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.service.impl

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.datasource.DataSourceColumn
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.repository.DataSourceColumnRepository
import spock.lang.Specification

class DataSourceColumnServiceImplTest extends Specification {

    private DataSourceColumnRepository dataSourceColumnRepository
    private DataSourceColumnServiceImpl dataSourceColumnService

    void setup() {
        dataSourceColumnRepository = Mock()
        dataSourceColumnService = new DataSourceColumnServiceImpl(dataSourceColumnRepository)
    }

    def "should find DataSourceColumn"() {
        given:
        final Long id = 1L

        final DataSourceColumn dataSourceColumn = [] as DataSourceColumn

        dataSourceColumnRepository.findOne(id) >> dataSourceColumn

        when:
        final DataSourceColumn foundDataSourceColumn = dataSourceColumnService.find(id)

        then:
        foundDataSourceColumn != null
        foundDataSourceColumn == dataSourceColumn

        1 * dataSourceColumnRepository.findOne(id) >> dataSourceColumn
        0 * _
    }
}

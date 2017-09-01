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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.service;

import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.impl.file.DataFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DataFileService {

    DataFile find(final Long id);

    Page<DataFile> find(final String jsonCriteria, final Pageable pageable);

    Page<DataFile> findAll(final Pageable pageable);

    DataFile create(final DataFile dataFile);

    DataFile update(final DataFile dataFile);

    void delete(final Long id);
}

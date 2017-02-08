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

package com.abixen.platform.service.businessintelligence.multivisualisation.util;


import com.abixen.platform.service.businessintelligence.multivisualisation.dto.DataFileColumnDTO;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.file.DataFile;

import java.util.Set;

public interface DataFileBuilder {

    DataFile build();

    DataFileBuilder base(String name, String description);

    DataFileBuilder data(Set<DataFileColumnDTO> dataColumn);
}

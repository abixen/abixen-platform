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

package com.abixen.platform.service.businessintelligence.multivisualization.model.impl.file;

import com.abixen.platform.service.businessintelligence.multivisualization.model.impl.data.DataColumn;

import javax.persistence.*;

@Entity
@DiscriminatorValue("FILE")
public class DataFileColumn extends DataColumn {

    @OneToOne
    @JoinColumn(name = "data_file_id", nullable = false)
    private DataFile dataFile;

}

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

package com.abixen.platform.service.businessintelligence.multivisualisation.application.dto;

import com.abixen.platform.common.dto.AuditingDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.enumtype.DatabaseType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class DatabaseConnectionDto extends AuditingDto {
    private Long id;
    private String name;
    private String description;
    private DatabaseType databaseType;
    private String databaseHost;
    private Integer databasePort;
    private String databaseName;
    private String username;
    private String password;
}
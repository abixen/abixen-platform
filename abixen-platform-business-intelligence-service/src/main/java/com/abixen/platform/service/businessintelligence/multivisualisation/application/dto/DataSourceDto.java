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


import com.abixen.platform.common.application.dto.SimpleAuditingDto;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.datasource.DataSourceType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;


@Getter
@Setter
@Accessors(chain = true)
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "classType")
@JsonSubTypes({@JsonSubTypes.Type(value = DatabaseDataSourceDto.class, name = "DB"),
        @JsonSubTypes.Type(value = FileDataSourceDto.class, name = "FILE")})
public class DataSourceDto extends SimpleAuditingDto {
    private Long id;
    private String name;
    private String description;
    private Set<DataSourceColumnDto> columns;
    private String filter;
    private DataSourceType dataSourceType;
}
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

package com.abixen.platform.service.businessintelligence.multivisualisation.model.web;

import com.abixen.platform.common.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DatabaseType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database.DatabaseConnection;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(as = DatabaseConnection.class)
public interface DatabaseConnectionWeb {

    @JsonView(WebModelJsonSerialize.class)
    Long getId();

    @JsonView(WebModelJsonSerialize.class)
    String getName();

    @JsonView(WebModelJsonSerialize.class)
    String getDescription();

    @JsonView(WebModelJsonSerialize.class)
    DatabaseType getDatabaseType();

    @JsonView(WebModelJsonSerialize.class)
    String getDatabaseHost();

    @JsonView(WebModelJsonSerialize.class)
    Integer getDatabasePort();

    @JsonView(WebModelJsonSerialize.class)
    String getDatabaseName();

    @JsonView(WebModelJsonSerialize.class)
    String getUsername();

    @JsonView(WebModelJsonSerialize.class)
    String getPassword();


}

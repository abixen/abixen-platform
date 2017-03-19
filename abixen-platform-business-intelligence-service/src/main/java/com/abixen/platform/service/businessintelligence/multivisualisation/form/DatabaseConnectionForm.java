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

package com.abixen.platform.service.businessintelligence.multivisualisation.form;

import com.abixen.platform.common.form.Form;
import com.abixen.platform.common.util.WebModelJsonSerialize;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DatabaseType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database.DatabaseConnection;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database.DatabaseConnection.*;


public class DatabaseConnectionForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    private Long id;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    @Size(max = NAME_MAX_LENGTH)
    private String name;

    @JsonView(WebModelJsonSerialize.class)
    @Size(max = DESCRIPTION_MAX_LENGTH)
    private String description;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private DatabaseType databaseType;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    @Size(max = DATABASE_HOST_MAX_LENGTH)
    private String databaseHost;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    @Min(0)
    private Integer databasePort;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    @Size(max = DATABASE_NAME_MAX_LENGTH)
    private String databaseName;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    @Size(max = USERNAME_MAX_LENGTH)
    private String username;

    @JsonView(WebModelJsonSerialize.class)
    @Size(max = PASSWORD_MAX_LENGTH)
    private String password;

    public DatabaseConnectionForm() {
    }

    public DatabaseConnectionForm(DatabaseConnection databaseConnection) {
        this.id = databaseConnection.getId();
        this.name = databaseConnection.getName();
        this.description = databaseConnection.getDescription();
        this.databaseType = databaseConnection.getDatabaseType();
        this.databaseHost = databaseConnection.getDatabaseHost();
        this.username = databaseConnection.getUsername();
        this.password = databaseConnection.getPassword();
        this.databaseHost = databaseConnection.getDatabaseHost();
        this.databasePort = databaseConnection.getDatabasePort();
        this.databaseName = databaseConnection.getDatabaseName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    public void setDatabaseHost(String databaseHost) {
        this.databaseHost = databaseHost;
    }

    public Integer getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(Integer databasePort) {
        this.databasePort = databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

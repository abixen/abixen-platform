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

package com.abixen.platform.service.businessintelligence.multivisualisation.model.impl.database;

import com.abixen.platform.common.util.ModelKeys;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.enumtype.DatabaseType;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.util.converter.ConnectionPasswordConverter;
import com.abixen.platform.common.model.audit.AuditingModel;
import com.abixen.platform.service.businessintelligence.multivisualisation.model.web.DatabaseConnectionWeb;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "database_connection")
@SequenceGenerator(sequenceName = "database_connection_seq", name = "database_connection_seq", allocationSize = 1)
public class DatabaseConnection extends AuditingModel implements DatabaseConnectionWeb, Serializable {

    private static final long serialVersionUID = -1411930471159410093L;

    public static final int NAME_MAX_LENGTH = 40;
    public static final int DESCRIPTION_MAX_LENGTH = 1000;
    public static final int DATABASE_HOST_MAX_LENGTH = 255;
    public static final int DATABASE_NAME_MAX_LENGTH = 255;
    public static final int USERNAME_MAX_LENGTH = 40;
    public static final int PASSWORD_MAX_LENGTH = 40;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "database_connection_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = ModelKeys.NAME_MAX_LENGTH, nullable = false)
    @NotNull
    @Size(max = NAME_MAX_LENGTH)
    private String name;

    @Column(name = "description", nullable = true)
    @Size(max = DESCRIPTION_MAX_LENGTH)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "database_type", nullable = false)
    @NotNull
    private DatabaseType databaseType;

    @Column(name = "database_host")
    @NotNull
    @Size(max = DATABASE_HOST_MAX_LENGTH)
    private String databaseHost;

    @Column(name = "database_port")
    @NotNull
    @Min(0)
    private Integer databasePort;

    @Column(name = "database_name")
    @NotNull
    @Size(max = DATABASE_NAME_MAX_LENGTH)
    private String databaseName;

    @Column(name = "username")
    @NotNull
    @Size(max = USERNAME_MAX_LENGTH)
    private String username;

    @Column(name = "password")
    @Convert(converter = ConnectionPasswordConverter.class)
    @Size(max = PASSWORD_MAX_LENGTH)
    private String password;

    @Override
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
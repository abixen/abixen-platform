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

package com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.connection;

import com.abixen.platform.common.domain.model.EntityBuilder;
import com.abixen.platform.common.domain.model.audit.SimpleAuditingModel;
import com.abixen.platform.common.infrastructure.util.ModelKeys;
import com.abixen.platform.service.businessintelligence.multivisualisation.domain.model.util.ConnectionPasswordConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "database_connection")
@SequenceGenerator(sequenceName = "database_connection_seq", name = "database_connection_seq", allocationSize = 1)
public final class DatabaseConnection extends SimpleAuditingModel implements Serializable {

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

    private DatabaseConnection() {
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    private void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public String getDatabaseHost() {
        return databaseHost;
    }

    private void setDatabaseHost(String databaseHost) {
        this.databaseHost = databaseHost;
    }

    public Integer getDatabasePort() {
        return databasePort;
    }

    private void setDatabasePort(Integer databasePort) {
        this.databasePort = databasePort;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    private void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public void changeCredentials(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public void changeDetails(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public void changeDatabase(DatabaseType databaseType,
                               String databaseHost,
                               Integer databasePort,
                               String databaseName) {
        setDatabaseType(databaseType);
        setDatabaseHost(databaseHost);
        setDatabasePort(databasePort);
        setDatabaseName(databaseName);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<DatabaseConnection> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new DatabaseConnection();
        }

        public Builder credentials(String username, String password) {
            this.product.setUsername(username);
            this.product.setPassword(password);
            return this;
        }

        public Builder details(String name, String description) {
            this.product.setName(name);
            this.product.setDescription(description);
            return this;
        }

        public Builder database(DatabaseType databaseType,
                                String databaseHost,
                                Integer databasePort,
                                String databaseName) {
            this.product.setDatabaseType(databaseType);
            this.product.setDatabaseHost(databaseHost);
            this.product.setDatabasePort(databasePort);
            this.product.setDatabaseName(databaseName);
            return this;
        }
    }

}
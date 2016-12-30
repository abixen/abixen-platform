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

package com.abixen.platform.core.model.impl;

import com.abixen.platform.core.model.ModuleTypeBase;
import com.abixen.platform.core.model.web.ModuleTypeWeb;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;


@JsonSerialize(as = ModuleType.class)
@Entity
@Table(name = "module_type")
@SequenceGenerator(sequenceName = "module_type_seq", name = "module_type_seq", allocationSize = 1)
public class ModuleType extends AuditingModel implements ModuleTypeBase, ModuleTypeWeb {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "module_type_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = MODULETYPE_NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "title", length = MODULETYPE_TITLE_MAX_LENGTH, nullable = false)
    private String title;

    @Column(name = "description", length = MODULETYPE_DESCRIPTION_MAX_LENGTH, nullable = false)
    private String description;

    @Column(name = "init_url")
    private String initUrl;

    @Column(name = "service_id", length = RESOURCE_SERVICE_ID_MAX_LENGTH, nullable = false)
    private String serviceId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getInitUrl() {
        return initUrl;
    }

    @Override
    public void setInitUrl(String initUrl) {
        this.initUrl = initUrl;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "ModuleType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initUrl='" + initUrl + '\'' +
                ", serviceId='" + serviceId + '\'' +
                '}';
    }
}

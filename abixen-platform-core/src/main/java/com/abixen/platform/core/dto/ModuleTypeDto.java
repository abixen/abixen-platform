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

package com.abixen.platform.core.dto;

import com.abixen.platform.core.model.impl.ModuleType;

import java.io.Serializable;


public class ModuleTypeDto implements Serializable {

    private final long serialVersionUID = -7437477767496577712L;

    private Long id;
    private String name;
    private String title;
    private String description;
    private String initUrl;
    private String serviceId;

    public ModuleTypeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ModuleTypeDto(ModuleType moduleType) {
        this.id = moduleType.getId();
        this.name = moduleType.getName();
        this.title = moduleType.getTitle();
        this.description = moduleType.getDescription();
        this.initUrl = moduleType.getInitUrl();
        this.serviceId = moduleType.getServiceId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getInitUrl() {
        return initUrl;
    }

    public String getServiceId() {
        return serviceId;
    }

}
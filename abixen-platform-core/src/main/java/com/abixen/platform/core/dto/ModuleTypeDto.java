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

import com.abixen.platform.common.model.ModuleTypeBase;
import lombok.ToString;

import java.util.List;


@ToString
public class ModuleTypeDto extends AuditingDto implements ModuleTypeBase<AdminSidebarItemDto, ResourceDto> {

    private Long id;
    private String name;
    private String angularJsNameApplication;
    private String angularJsNameAdmin;
    private String title;
    private String description;
    private String initUrl;
    private String serviceId;
    private List<ResourceDto> resources;
    private List<AdminSidebarItemDto> adminSidebarItems;

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
    public String getAngularJsNameApplication() {
        return angularJsNameApplication;
    }

    @Override
    public void setAngularJsNameApplication(String angularJsNameApplication) {
        this.angularJsNameApplication = angularJsNameApplication;
    }

    @Override
    public String getAngularJsNameAdmin() {
        return angularJsNameAdmin;
    }

    @Override
    public void setAngularJsNameAdmin(String angularJsNameAdmin) {
        this.angularJsNameAdmin = angularJsNameAdmin;
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
    public List<ResourceDto> getResources() {
        return resources;
    }

    @Override
    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    @Override
    public List<AdminSidebarItemDto> getAdminSidebarItems() {
        return adminSidebarItems;
    }

    @Override
    public void setAdminSidebarItems(List<AdminSidebarItemDto> adminSidebarItems) {
        this.adminSidebarItems = adminSidebarItems;
    }
}
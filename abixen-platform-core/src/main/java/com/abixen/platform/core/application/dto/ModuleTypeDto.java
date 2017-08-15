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

package com.abixen.platform.core.application.dto;

import lombok.ToString;

import java.util.List;


@ToString
public class ModuleTypeDto extends AuditingDto {

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

    public String getAngularJsNameApplication() {
        return angularJsNameApplication;
    }

    public void setAngularJsNameApplication(String angularJsNameApplication) {
        this.angularJsNameApplication = angularJsNameApplication;
    }

    public String getAngularJsNameAdmin() {
        return angularJsNameAdmin;
    }

    public void setAngularJsNameAdmin(String angularJsNameAdmin) {
        this.angularJsNameAdmin = angularJsNameAdmin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitUrl() {
        return initUrl;
    }

    public void setInitUrl(String initUrl) {
        this.initUrl = initUrl;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    public List<AdminSidebarItemDto> getAdminSidebarItems() {
        return adminSidebarItems;
    }

    public void setAdminSidebarItems(List<AdminSidebarItemDto> adminSidebarItems) {
        this.adminSidebarItems = adminSidebarItems;
    }
}
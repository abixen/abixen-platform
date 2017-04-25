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

import com.abixen.platform.common.model.ModuleTypeBase;
import com.abixen.platform.common.model.SecurableModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.List;


@JsonSerialize(as = ModuleType.class)
@Entity
@Table(name = "module_type")
@SequenceGenerator(sequenceName = "module_type_seq", name = "module_type_seq", allocationSize = 1)
public class ModuleType extends AuditingModel implements ModuleTypeBase<AdminSidebarItem, Resource>, SecurableModel<User> {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "module_type_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = MODULETYPE_NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "angular_js_name_application", length = MODULETYPE_ANGULAR_JS_NAME_MAX_LENGTH, nullable = false)
    private String angularJsNameApplication;

    @Column(name = "angular_js_name_admin", length = MODULETYPE_ANGULAR_JS_NAME_MAX_LENGTH, nullable = false)
    private String angularJsNameAdmin;

    @Column(name = "title", length = MODULETYPE_TITLE_MAX_LENGTH, nullable = false)
    private String title;

    @Column(name = "description", length = MODULETYPE_DESCRIPTION_MAX_LENGTH, nullable = false)
    private String description;

    @Column(name = "init_url")
    private String initUrl;

    @Column(name = "service_id", length = RESOURCE_SERVICE_ID_MAX_LENGTH, nullable = false)
    private String serviceId;

    @OneToMany(mappedBy = "moduleType")
    private List<Resource> resources;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "module_type_admin_sidebar_item",
            joinColumns = {@JoinColumn(
                    name = "module_type_id",
                    nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(
                    name = "admin_sidebar_item_id",
                    nullable = false,
                    updatable = false)})
    private List<AdminSidebarItem> adminSidebarItems;

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
    public List<Resource> getResources() {
        return resources;
    }

    @Override
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public List<AdminSidebarItem> getAdminSidebarItems() {
        return adminSidebarItems;
    }

    @Override
    public void setAdminSidebarItems(List<AdminSidebarItem> adminSidebarItems) {
        this.adminSidebarItems = adminSidebarItems;
    }

}
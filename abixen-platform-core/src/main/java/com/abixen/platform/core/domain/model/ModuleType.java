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

package com.abixen.platform.core.domain.model;

import com.abixen.platform.common.domain.model.EntityBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table(name = "module_type")
@SequenceGenerator(sequenceName = "module_type_seq", name = "module_type_seq", allocationSize = 1)
public final class ModuleType extends AuditingModel implements SecurableModel {

    public static final int MODULETYPE_NAME_MIN_LENGTH = 5;
    public static final int MODULETYPE_NAME_MAX_LENGTH = 20;
    public static final int MODULETYPE_ANGULAR_JS_NAME_MAX_LENGTH = 100;
    public static final int MODULETYPE_TITLE_MIN_LENGTH = 6;
    public static final int MODULETYPE_TITLE_MAX_LENGTH = 40;
    public static final int MODULETYPE_DESCRIPTION_MIN_LENGTH = 40;
    public static final int MODULETYPE_DESCRIPTION_MAX_LENGTH = 40;
    public static final int RESOURCE_SERVICE_ID_MIN_LENGTH = 1;
    public static final int RESOURCE_SERVICE_ID_MAX_LENGTH = 255;

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

    private ModuleType() {
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

    public String getAngularJsNameApplication() {
        return angularJsNameApplication;
    }

    private void setAngularJsNameApplication(String angularJsNameApplication) {
        this.angularJsNameApplication = angularJsNameApplication;
    }

    public String getAngularJsNameAdmin() {
        return angularJsNameAdmin;
    }

    private void setAngularJsNameAdmin(String angularJsNameAdmin) {
        this.angularJsNameAdmin = angularJsNameAdmin;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getInitUrl() {
        return initUrl;
    }

    private void setInitUrl(String initUrl) {
        this.initUrl = initUrl;
    }

    public String getServiceId() {
        return serviceId;
    }

    private void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public List<Resource> getResources() {
        return resources;
    }

    private void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<AdminSidebarItem> getAdminSidebarItems() {
        return adminSidebarItems;
    }

    private void setAdminSidebarItems(List<AdminSidebarItem> adminSidebarItems) {
        this.adminSidebarItems = adminSidebarItems;
    }

    public void changeDescription(String description) {
        setDescription(description);
    }

    public void changeTitle(String title) {
        setTitle(title);
    }

    public void changeInitUrl(String initUrl) {
        setInitUrl(initUrl);
    }

    public void changeAdminSidebarItems(List<AdminSidebarItem> adminSidebarItems) {
        setAdminSidebarItems(adminSidebarItems);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<ModuleType> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new ModuleType();
        }

        @Override
        protected ModuleType assembleProduct() {
            return this.product;
        }

        public Builder basic(String name, String title, String description) {
            this.product.setName(name);
            this.product.setTitle(title);
            this.product.setDescription(description);
            return this;
        }

        public Builder angular(String angularJsNameApplication, String angularJsNameAdmin) {
            this.product.setAngularJsNameApplication(angularJsNameApplication);
            this.product.setAngularJsNameAdmin(angularJsNameAdmin);
            return this;
        }

        public Builder initUrl(String initUrl) {
            this.product.setInitUrl(initUrl);
            return this;
        }

        public Builder serviceId(String serviceId) {
            this.product.setServiceId(serviceId);
            return this;
        }

        public Builder adminSidebarItems(List<AdminSidebarItem> adminSidebarItems) {
            this.product.setAdminSidebarItems(adminSidebarItems);
            return this;
        }
    }
}
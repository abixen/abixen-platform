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

import com.abixen.platform.common.domain.model.enumtype.ResourcePage;
import com.abixen.platform.common.domain.model.enumtype.ResourcePageLocation;
import com.abixen.platform.common.domain.model.enumtype.ResourceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@JsonSerialize(as = Resource.class)
@Entity
@Table(name = "resource")
@SequenceGenerator(sequenceName = "resource_seq", name = "resource_seq", allocationSize = 1)
public class Resource extends AuditingModel {

    public static final int RESOURCE_RELATIVE_URL_MIN_LENGTH = 3;
    public static final int RESOURCE_RELATIVE_URL_MAX_LENGTH = 250;

    //FIXME - to remove AuditingModel
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "resource_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "relative_url", length = RESOURCE_RELATIVE_URL_MAX_LENGTH, nullable = false)
    private String relativeUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_page_location", nullable = false)
    private ResourcePageLocation resourcePageLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_page", nullable = false)
    private ResourcePage resourcePage;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", nullable = false)
    private ResourceType resourceType;

    @JsonIgnore
    @JoinColumn(name = "module_type_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private ModuleType moduleType;

    Resource() {
    }

    @Override
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    public ResourcePageLocation getResourcePageLocation() {
        return resourcePageLocation;
    }

    void setResourcePageLocation(ResourcePageLocation resourcePageLocation) {
        this.resourcePageLocation = resourcePageLocation;
    }

    public ResourcePage getResourcePage() {
        return resourcePage;
    }

    void setResourcePage(ResourcePage resourcePage) {
        this.resourcePage = resourcePage;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }
}

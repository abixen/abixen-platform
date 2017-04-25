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

import com.abixen.platform.common.model.ResourceBase;
import com.abixen.platform.common.model.enumtype.ResourcePage;
import com.abixen.platform.common.model.enumtype.ResourcePageLocation;
import com.abixen.platform.common.model.enumtype.ResourceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;


@JsonSerialize(as = Resource.class)
@Entity
@Table(name = "resource")
@SequenceGenerator(sequenceName = "resource_seq", name = "resource_seq", allocationSize = 1)
public class Resource extends AuditingModel implements ResourceBase<ModuleType> {


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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getRelativeUrl() {
        return relativeUrl;
    }

    @Override
    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    @Override
    public ResourcePageLocation getResourcePageLocation() {
        return resourcePageLocation;
    }

    @Override
    public void setResourcePageLocation(ResourcePageLocation resourcePageLocation) {
        this.resourcePageLocation = resourcePageLocation;
    }

    @Override
    public ResourcePage getResourcePage() {
        return resourcePage;
    }

    @Override
    public void setResourcePage(ResourcePage resourcePage) {
        this.resourcePage = resourcePage;
    }

    @Override
    public ResourceType getResourceType() {
        return resourceType;
    }

    @Override
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public ModuleType getModuleType() {
        return moduleType;
    }

    @Override
    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }
}

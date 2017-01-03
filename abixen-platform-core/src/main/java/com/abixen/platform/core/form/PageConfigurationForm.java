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

package com.abixen.platform.core.form;

import com.abixen.platform.core.dto.DashboardModuleDto;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.model.web.PageWeb;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public class PageConfigurationForm implements Form {

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private PageWeb page;

    @JsonView(WebModelJsonSerialize.class)
    @NotNull
    private List<DashboardModuleDto> dashboardModuleDtos;

    public PageConfigurationForm() {
    }

    public PageConfigurationForm(Page page) {
        this.page = page;
        this.dashboardModuleDtos = new ArrayList<>();
    }

    public PageConfigurationForm(Page page, List<DashboardModuleDto> dashboardModuleDtos) {
        this.page = page;
        this.dashboardModuleDtos = dashboardModuleDtos;
    }

    public PageWeb getPage() {
        return page;
    }

    public void setPage(PageWeb page) {
        this.page = page;
    }

    public List<DashboardModuleDto> getDashboardModuleDtos() {
        return dashboardModuleDtos;
    }

    public void setDashboardModuleDtos(List<DashboardModuleDto> dashboardModuleDtos) {
        this.dashboardModuleDtos = dashboardModuleDtos;
    }

}
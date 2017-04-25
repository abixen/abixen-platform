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

import com.abixen.platform.common.form.Form;
import com.abixen.platform.core.dto.DashboardModuleDto;
import com.abixen.platform.core.dto.PageDto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public class PageConfigurationForm implements Form {

    @NotNull
    private PageDto page;

    @NotNull
    private List<DashboardModuleDto> dashboardModuleDtos;

    public PageConfigurationForm() {
    }

    public PageConfigurationForm(PageDto page) {
        this.page = page;
        this.dashboardModuleDtos = new ArrayList<>();
    }

    public PageConfigurationForm(PageDto page, List<DashboardModuleDto> dashboardModuleDtos) {
        this.page = page;
        this.dashboardModuleDtos = dashboardModuleDtos;
    }

    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }

    public List<DashboardModuleDto> getDashboardModuleDtos() {
        return dashboardModuleDtos;
    }

    public void setDashboardModuleDtos(List<DashboardModuleDto> dashboardModuleDtos) {
        this.dashboardModuleDtos = dashboardModuleDtos;
    }

}
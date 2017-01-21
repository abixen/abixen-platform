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

package com.abixen.platform.core.service;

import com.abixen.platform.core.dto.DashboardModuleDto;
import com.abixen.platform.core.form.ModuleForm;
import com.abixen.platform.core.form.ModuleSearchForm;
import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.impl.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ModuleService {

    //@PreAuthorize("hasPermission(#page, 'Module', 'VIEW')")
    Module createModule(Module module);

    Module updateModule(Module module);

    ModuleForm updateModule(ModuleForm moduleForm);

    Module findModule(Long id);

    List<Module> findAllByPage(Page page);

    void removeAllExcept(Page page, List<Long> ids);

    void removeAll(Page page);

    Module buildModule(DashboardModuleDto dashboardModuleDto, Page page);

    org.springframework.data.domain.Page<Module> findAllModules(Pageable pageable, ModuleSearchForm moduleSearchForm);

}

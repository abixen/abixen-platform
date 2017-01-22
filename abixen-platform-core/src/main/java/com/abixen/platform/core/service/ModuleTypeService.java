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

import com.abixen.platform.core.dto.ModuleTypeDto;
import com.abixen.platform.core.form.ModuleTypeSearchForm;
import com.abixen.platform.core.model.impl.ModuleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ModuleTypeService {

    ModuleType findModuleType(Long id);

    List<ModuleTypeDto> findAllModuleTypes();

    Page<ModuleType> findModuleTypes(Pageable pageable, ModuleTypeSearchForm moduleTypeSearchForm);

    List<ModuleType> findModuleTypes();

    void reload(Long id);

    void reloadAll();
}
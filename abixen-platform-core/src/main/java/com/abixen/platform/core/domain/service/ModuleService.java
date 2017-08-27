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

package com.abixen.platform.core.domain.service;

import com.abixen.platform.core.application.form.ModuleSearchForm;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ModuleService {

    Module find(Long id);

    List<Module> findAll(com.abixen.platform.core.domain.model.Page page);

    org.springframework.data.domain.Page<Module> findAll(Pageable pageable, ModuleSearchForm moduleSearchForm, User authorizedUser);

    Module create(Module module);

    Module update(Module module);

    void deleteAll(List<Module> modules);

    void deleteAllExcept(com.abixen.platform.core.domain.model.Page page, List<Long> ids);

}
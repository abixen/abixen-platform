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

package com.abixen.platform.core.application.service;

import com.abixen.platform.common.infrastructure.annotation.PlatformApplicationService;
import com.abixen.platform.common.infrastructure.security.PlatformUser;
import com.abixen.platform.core.application.converter.ModuleToModuleDtoConverter;
import com.abixen.platform.core.application.dto.ModuleDto;
import com.abixen.platform.core.application.form.ModuleForm;
import com.abixen.platform.core.application.form.ModuleSearchForm;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.service.ModuleService;
import com.abixen.platform.core.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

@Slf4j
@PlatformApplicationService
public class ModuleManagementService {

    private final SecurityService securityService;
    private final ModuleService moduleService;
    private final UserService userService;
    private final ModuleToModuleDtoConverter moduleToModuleDtoConverter;

    @Autowired
    public ModuleManagementService(SecurityService securityService,
                                   ModuleService moduleService,
                                   UserService userService,
                                   ModuleToModuleDtoConverter moduleToModuleDtoConverter) {
        this.securityService = securityService;
        this.moduleService = moduleService;
        this.userService = userService;
        this.moduleToModuleDtoConverter = moduleToModuleDtoConverter;
    }

    public ModuleDto findModule(final Long id) {
        log.debug("findModule() - id: {}", id);

        final Module module = moduleService.find(id);

        return moduleToModuleDtoConverter.convert(module);
    }

    public org.springframework.data.domain.Page<ModuleDto> findAllModules(final Pageable pageable, final ModuleSearchForm moduleSearchForm) {
        log.debug("findAllModules() - pageable: {}, moduleSearchForm: {}", pageable, moduleSearchForm);

        final PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        final User authorizedUser = userService.find(platformAuthorizedUser.getId());

        final org.springframework.data.domain.Page<Module> modules = moduleService.findAll(pageable, moduleSearchForm, authorizedUser);

        return moduleToModuleDtoConverter.convertToPage(modules);

    }

    public ModuleForm updateModule(final ModuleForm moduleForm) {
        log.debug("updateModule() - moduleForm: {}", moduleForm);

        final Module module = moduleService.find(moduleForm.getId());
        module.changeTitle(moduleForm.getTitle());
        module.changeDescription(moduleForm.getDescription());

        final Module updatedModule = moduleService.update(module);

        return new ModuleForm(updatedModule);
    }

}
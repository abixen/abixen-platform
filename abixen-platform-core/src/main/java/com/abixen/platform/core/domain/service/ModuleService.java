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

import com.abixen.platform.common.domain.model.enumtype.PermissionName;
import com.abixen.platform.common.infrastructure.annotation.PlatformDomainService;
import com.abixen.platform.common.interfaces.amqp.command.DeleteModuleCommand;
import com.abixen.platform.core.application.form.ModuleSearchForm;
import com.abixen.platform.core.application.service.DeleteModuleService;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.repository.ModuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@PlatformDomainService
public class ModuleService {


    private final AclService aclService;
    private final CommentService commentService;
    private final ModuleRepository moduleRepository;
    private final DeleteModuleService deleteModuleService;

    @Autowired
    public ModuleService(AclService aclService,
                         CommentService commentService,
                         ModuleRepository moduleRepository,
                         DeleteModuleService deleteModuleService) {
        this.aclService = aclService;
        this.commentService = commentService;
        this.moduleRepository = moduleRepository;
        this.deleteModuleService = deleteModuleService;
    }

    public Module find(final Long id) {
        log.debug("find() - id: {}", id);

        return moduleRepository.findOne(id);
    }

    public List<Module> findAll(final com.abixen.platform.core.domain.model.Page page) {
        log.debug("findAll() - page: {}", page);

        return moduleRepository.findAll(page);
    }

    public Page<Module> findAll(final Pageable pageable, final ModuleSearchForm moduleSearchForm, final User authorizedUser) {
        log.debug("findAll() - pageable: {}, moduleSearchForm: {}, authorizedUser: {}", pageable, moduleSearchForm, authorizedUser);

        return moduleRepository.findAllSecured(pageable, moduleSearchForm, authorizedUser, PermissionName.MODULE_VIEW);
    }

    public Module create(final Module module) {
        log.debug("create() - module: {}", module);

        final Module createdModule = moduleRepository.save(module);

        aclService.createDefaultAcl(createdModule, new ArrayList<PermissionName>() {
            {
                add(PermissionName.MODULE_VIEW);
                add(PermissionName.MODULE_EDIT);
                add(PermissionName.MODULE_DELETE);
                add(PermissionName.MODULE_CONFIGURATION);
                add(PermissionName.MODULE_PERMISSION);
            }
        });

        return createdModule;
    }

    public Module update(final Module module) {
        log.debug("update() - module: {}", module);

        return moduleRepository.save(module);
    }

    public void deleteAll(final List<Module> modules) {
        log.debug("deleteAll() - modules: {}", modules);

        moduleRepository.deleteInBatch(modules);
    }

    public void deleteAllExcept(final com.abixen.platform.core.domain.model.Page page, final List<Long> ids) {
        log.debug("deleteAllExcept() - page: {}, {}", page, ids);

        if (ids.isEmpty()) {
            deleteAll(page);

            return;
        }

        final List<Module> modules = moduleRepository.findAllExcept(page, ids);

        final List<Long> moduleIds = modules.stream().map(module -> module.getId()).collect(Collectors.toList());

        if (!moduleIds.isEmpty()) {
            commentService.deleteAllByModuleIds(moduleIds);
        }
        moduleRepository.removeAllExcept(page, ids);

        modules.forEach(module -> {
            DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(module.getId(), module.getModuleType().getName());
            deleteModuleService.delete(module.getModuleType().getServiceId(), deleteModuleCommand);
        });
    }

    void deleteAll(com.abixen.platform.core.domain.model.Page page) {
        final List<Module> modules = moduleRepository.findAll(page);

        final List<Long> moduleIds = modules.stream().map(module -> module.getId()).collect(Collectors.toList());
        commentService.deleteAllByModuleIds(moduleIds);

        moduleRepository.removeAll(page);

        modules.forEach(module -> {
            DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(module.getId(), module.getModuleType().getName());
            deleteModuleService.delete(module.getModuleType().getServiceId(), deleteModuleCommand);
        });
    }

}
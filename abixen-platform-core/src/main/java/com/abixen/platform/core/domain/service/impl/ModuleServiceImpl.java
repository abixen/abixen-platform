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

package com.abixen.platform.core.domain.service.impl;

import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.common.rabbitmq.message.RabbitMQMessage;
import com.abixen.platform.common.rabbitmq.message.RabbitMQRemoveModuleMessage;
import com.abixen.platform.core.application.form.ModuleSearchForm;
import com.abixen.platform.core.application.service.AclService;
import com.abixen.platform.core.application.service.CommentService;
import com.abixen.platform.core.application.service.RabbitMQOperations;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.repository.ModuleRepository;
import com.abixen.platform.core.domain.service.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class ModuleServiceImpl implements ModuleService {


    private final AclService aclService;
    private final CommentService commentService;
    private final ModuleRepository moduleRepository;
    private final RabbitMQOperations rabbitMQOperations;

    @Autowired
    public ModuleServiceImpl(AclService aclService,
                             CommentService commentService,
                             ModuleRepository moduleRepository,
                             RabbitMQOperations rabbitMQOperations) {
        this.aclService = aclService;
        this.commentService = commentService;
        this.moduleRepository = moduleRepository;
        this.rabbitMQOperations = rabbitMQOperations;
    }

    @Override
    public Module find(final Long id) {
        log.debug("find() - id: {}", id);

        return moduleRepository.findOne(id);
    }

    @Override
    public List<Module> findAll(final com.abixen.platform.core.domain.model.Page page) {
        log.debug("findAll() - page: {}", page);

        return moduleRepository.findByPage(page);
    }

    @Override
    public Page<Module> findAll(final Pageable pageable, final ModuleSearchForm moduleSearchForm, final User authorizedUser) {
        log.debug("findAll() - pageable: {}, moduleSearchForm: {}, authorizedUser: {}", pageable, moduleSearchForm, authorizedUser);

        return moduleRepository.findAllSecured(pageable, moduleSearchForm, authorizedUser, PermissionName.MODULE_VIEW);
    }

    @Override
    public Module create(final Module module) {
        log.debug("create() - module: {}", module);

        final Module createdModule = moduleRepository.save(module);

        aclService.insertDefaultAcl(createdModule, new ArrayList<PermissionName>() {
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

    @Override
    public Module update(final Module module) {
        log.debug("update() - module: {}", module);

        return moduleRepository.save(module);
    }

    @Override
    public void deleteAll(final List<Module> modules) {
        log.debug("deleteAll() - modules: {}", modules);

        moduleRepository.deleteInBatch(modules);
    }

    @Override
    public void deleteAllExcept(final com.abixen.platform.core.domain.model.Page page, final List<Long> ids) {
        log.debug("deleteAllExcept() - page: {}, {}", page, ids);

        if (ids.isEmpty()) {
            deleteAll(page);

            return;
        }

        final List<Module> modules = moduleRepository.findAllExcept(page, ids);

        final List<Long> moduleIds = modules.stream().map(module -> module.getId()).collect(Collectors.toList());

        if (!moduleIds.isEmpty()) {
            commentService.deleteCommentByModuleIds(moduleIds);
        }
        moduleRepository.removeAllExcept(page, ids);

        modules.forEach(module -> {
            RabbitMQMessage removeMessage = new RabbitMQRemoveModuleMessage(module.getId(), module.getModuleType().getName());
            rabbitMQOperations.convertAndSend(module.getModuleType().getServiceId(), removeMessage);
        });
    }

    void deleteAll(com.abixen.platform.core.domain.model.Page page) {
        final List<Module> modules = moduleRepository.findByPage(page);

        final List<Long> moduleIds = modules.stream().map(module -> module.getId()).collect(Collectors.toList());
        commentService.deleteCommentByModuleIds(moduleIds);

        moduleRepository.removeAll(page);

        modules.forEach(module -> {
            RabbitMQMessage removeMessage = new RabbitMQRemoveModuleMessage(module.getId(), module.getModuleType().getName());
            rabbitMQOperations.convertAndSend(module.getModuleType().getServiceId(), removeMessage);
        });
    }

}
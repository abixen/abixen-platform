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

package com.abixen.platform.core.application.service.impl;

import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.common.rabbitmq.message.RabbitMQMessage;
import com.abixen.platform.common.rabbitmq.message.RabbitMQRemoveModuleMessage;
import com.abixen.platform.common.security.PlatformUser;
import com.abixen.platform.core.application.form.ModuleForm;
import com.abixen.platform.core.application.form.ModuleSearchForm;
import com.abixen.platform.core.application.service.AclService;
import com.abixen.platform.core.application.service.CommentService;
import com.abixen.platform.core.application.service.ModuleService;
import com.abixen.platform.core.application.service.RabbitMQOperations;
import com.abixen.platform.core.application.service.SecurityService;
import com.abixen.platform.core.domain.service.UserService;
import com.abixen.platform.core.domain.model.Module;
import com.abixen.platform.core.domain.model.Page;
import com.abixen.platform.core.domain.model.User;
import com.abixen.platform.core.domain.repository.ModuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ModuleServiceImpl implements ModuleService {

    private final SecurityService securityService;
    private final UserService userService;
    private final ModuleRepository moduleRepository;
    private final AclService aclService;
    private final RabbitMQOperations rabbitMQOperations;
    private final CommentService commentService;

    @Autowired
    public ModuleServiceImpl(SecurityService securityService,
                             UserService userService,
                             ModuleRepository moduleRepository,
                             AclService aclService,
                             RabbitMQOperations rabbitMQOperations,
                             CommentService commentService) {
        this.securityService = securityService;
        this.userService = userService;
        this.moduleRepository = moduleRepository;
        this.aclService = aclService;
        this.rabbitMQOperations = rabbitMQOperations;
        this.commentService = commentService;
    }

    @Override
    public Module create(Module module) {
        log.debug("create() - module: " + module);
        Module createdModule = moduleRepository.save(module);
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
    public ModuleForm update(ModuleForm moduleForm) {
        log.debug("update() - moduleForm: " + moduleForm);

        Module module = find(moduleForm.getId());
        module.changeTitle(moduleForm.getTitle());
        module.changeDescription(moduleForm.getDescription());

        return new ModuleForm(update(module));
    }

    @Override
    public Module update(Module module) {
        log.debug("update() - module: " + module);
        return moduleRepository.save(module);
    }

    @Override
    public Module find(Long id) {
        log.debug("find() - id: " + id);
        return moduleRepository.findOne(id);
    }

    @Override
    public List<Module> findAll(Page page) {
        log.debug("findAll() - page: " + page);
        return moduleRepository.findByPage(page);
    }

    @Override
    public void deleteAllExcept(Page page, List<Long> ids) {
        log.debug("deleteAllExcept() - page: " + page + ", ids: " + ids);

        if (ids.isEmpty()) {
            deleteAll(page);
            return;
        }

        List<Module> modules = moduleRepository.findAllExcept(page, ids);

        List<Long> moduleIds = modules.stream().map(module -> module.getId()).collect(Collectors.toList());

        if (!moduleIds.isEmpty()) {
            commentService.deleteCommentByModuleIds(moduleIds);
        }
        moduleRepository.removeAllExcept(page, ids);

        modules.forEach(module -> {
            RabbitMQMessage removeMessage = new RabbitMQRemoveModuleMessage(module.getId(), module.getModuleType().getName());
            rabbitMQOperations.convertAndSend(module.getModuleType().getServiceId(), removeMessage);
        });
    }

    void deleteAll(Page page) {
        log.debug("deleteAll() - page: " + page);

        List<Module> modules = moduleRepository.findByPage(page);

        List<Long> moduleIds = modules.stream().map(module -> module.getId()).collect(Collectors.toList());
        commentService.deleteCommentByModuleIds(moduleIds);

        moduleRepository.removeAll(page);

        modules.forEach(module -> {
            RabbitMQMessage removeMessage = new RabbitMQRemoveModuleMessage(module.getId(), module.getModuleType().getName());
            rabbitMQOperations.convertAndSend(module.getModuleType().getServiceId(), removeMessage);
        });
    }

    @Override
    public org.springframework.data.domain.Page<Module> findAll(Pageable pageable, ModuleSearchForm moduleSearchForm) {
        log.debug("findAll() - pageable: " + pageable);
        PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        User authorizedUser = userService.find(platformAuthorizedUser.getId());

        return moduleRepository.findAllSecured(pageable, moduleSearchForm, authorizedUser, PermissionName.MODULE_VIEW);
    }

    @Override
    public void deleteAll(List<Module> modules) {
        log.debug("deleteAll() - modules: {}", modules);

        moduleRepository.deleteInBatch(modules);
    }
}
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

package com.abixen.platform.core.service.impl;

import com.abixen.platform.core.dto.DashboardModuleDto;
import com.abixen.platform.core.form.ModuleForm;
import com.abixen.platform.core.form.ModuleSearchForm;
import com.abixen.platform.common.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.model.impl.User;
import com.abixen.platform.common.rabbitmq.message.RabbitMQMessage;
import com.abixen.platform.common.rabbitmq.message.RabbitMQRemoveModuleMessage;
import com.abixen.platform.core.repository.ModuleRepository;
import com.abixen.platform.common.security.PlatformUser;
import com.abixen.platform.core.service.*;
import com.abixen.platform.core.util.ModuleBuilder;
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
    private final ModuleTypeService moduleTypeService;
    private final DomainBuilderService domainBuilderService;
    private final AclService aclService;
    private final RabbitMQOperations rabbitMQOperations;
    private final CommentService commentService;

    @Autowired
    public ModuleServiceImpl(SecurityService securityService,
                             UserService userService,
                             ModuleRepository moduleRepository,
                             ModuleTypeService moduleTypeService,
                             DomainBuilderService domainBuilderService,
                             AclService aclService,
                             RabbitMQOperations rabbitMQOperations,
                             CommentService commentService) {
        this.securityService = securityService;
        this.userService = userService;
        this.moduleRepository = moduleRepository;
        this.moduleTypeService = moduleTypeService;
        this.domainBuilderService = domainBuilderService;
        this.aclService = aclService;
        this.rabbitMQOperations = rabbitMQOperations;
        this.commentService = commentService;
    }

    @Override
    public Module createModule(Module module) {
        log.debug("createModule() - module: " + module);
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
    public ModuleForm updateModule(ModuleForm moduleForm) {
        log.debug("updateModule() - moduleForm: " + moduleForm);

        Module module = findModule(moduleForm.getId());
        module.setTitle(moduleForm.getTitle());
        module.setDescription(moduleForm.getDescription());

        return new ModuleForm(updateModule(module));
    }

    @Override
    public Module updateModule(Module module) {
        log.debug("updateModule() - module: " + module);
        return moduleRepository.save(module);
    }

    @Override
    public Module findModule(Long id) {
        log.debug("findModule() - id: " + id);
        return moduleRepository.findOne(id);
    }

    @Override
    public List<Module> findAllByPage(Page page) {
        log.debug("findAllByPage() - page: " + page);
        return moduleRepository.findByPage(page);
    }

    @Override
    public void removeAllExcept(Page page, List<Long> ids) {
        log.debug("removeAllExcept() - page: " + page + ", ids: " + ids);

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

    @Override
    public void removeAll(Page page) {
        log.debug("removeAll() - page: " + page);

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
    public Module buildModule(DashboardModuleDto dashboardModuleDto, Page page) {
        log.debug("buildModule() - dashboardModuleDto: " + dashboardModuleDto);

        ModuleBuilder moduleBuilder = domainBuilderService.newModuleBuilderInstance();
        moduleBuilder.positionIndexes(dashboardModuleDto.getRowIndex(), dashboardModuleDto.getColumnIndex(), dashboardModuleDto.getOrderIndex());
        moduleBuilder.moduleData(dashboardModuleDto.getTitle(), moduleTypeService.findModuleType(dashboardModuleDto.getModuleType().getId()), page);
        moduleBuilder.description((dashboardModuleDto.getDescription()));

        return moduleBuilder.build();
    }

    @Override
    public org.springframework.data.domain.Page<Module> findAllModules(Pageable pageable, ModuleSearchForm moduleSearchForm) {
        log.debug("findAllModules() - pageable: " + pageable);
        PlatformUser platformAuthorizedUser = securityService.getAuthorizedUser();
        User authorizedUser = userService.findUser(platformAuthorizedUser.getId());

        return moduleRepository.findAllSecured(pageable, moduleSearchForm, authorizedUser, PermissionName.MODULE_VIEW);
    }
}
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
import com.abixen.platform.core.model.enumtype.PermissionName;
import com.abixen.platform.core.model.impl.Module;
import com.abixen.platform.core.model.impl.Page;
import com.abixen.platform.core.repository.ModuleRepository;
import com.abixen.platform.core.repository.ModuleTypeRepository;
import com.abixen.platform.core.service.AclService;
import com.abixen.platform.core.service.DomainBuilderService;
import com.abixen.platform.core.service.ModuleService;
import com.abixen.platform.core.util.ModuleBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class ModuleServiceImpl implements ModuleService {

    private final Logger log = Logger.getLogger(ModuleServiceImpl.class.getName());

    @Resource
    private ModuleRepository moduleRepository;

    @Resource
    private ModuleTypeRepository moduleTypeRepository;

    @Autowired
    private DomainBuilderService domainBuilderService;

    @Autowired
    private AclService aclService;

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
        moduleRepository.removeAllExcept(page, ids);
    }

    @Override
    public void removeAll(Page page) {
        log.debug("removeAll() - page: " + page);
        moduleRepository.removeAll(page);
    }

    @Override
    public Module buildModule(DashboardModuleDto dashboardModuleDto, Page page) {
        log.debug("buildModule() - dashboardModuleDto: " + dashboardModuleDto);

        ModuleBuilder moduleBuilder = domainBuilderService.newModuleBuilderInstance();
        moduleBuilder.positionIndexes(dashboardModuleDto.getRowIndex(), dashboardModuleDto.getColumnIndex(), dashboardModuleDto.getOrderIndex());
        moduleBuilder.moduleData(dashboardModuleDto.getTitle(), moduleTypeRepository.findOne(dashboardModuleDto.getModuleType().getId()), page);
        moduleBuilder.description((dashboardModuleDto.getDescription()));

        return moduleBuilder.build();
    }

    @Override
    public org.springframework.data.domain.Page<Module> findAllModules(Pageable pageable) {
        log.debug("findAllModules() - pageable: " + pageable);
        return moduleRepository.findAll(pageable);
    }

}

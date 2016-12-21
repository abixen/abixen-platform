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

import com.abixen.platform.core.model.impl.Permission;
import com.abixen.platform.core.repository.PermissionRepository;
import com.abixen.platform.core.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionRepository permissionRepository;

    @Override
    public Page<Permission> findAllPermissions(Pageable pageable, String jsonCriteria) throws NoSuchFieldException {
        log.debug("findAllPermissions() - pageable: " + pageable + ", jsonCriteria: " + jsonCriteria);

        //TODO
        //return permissionRepository.findAllByJsonCriteria(jsonCriteria, pageable);
        return permissionRepository.findAll(pageable);
    }

    @Override
    public List<Permission> findAllPermissions() {
        log.debug("findAllPermissions()");
        return permissionRepository.findAll();
    }

    @Override
    public Permission findPermission(Long id) {
        log.debug("findPermission() - id: " + id);
        return permissionRepository.findOne(id);
    }

}

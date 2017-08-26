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

import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.common.model.enumtype.AclSidType;
import com.abixen.platform.core.application.form.RoleSearchForm;
import com.abixen.platform.core.domain.model.Role;
import com.abixen.platform.core.domain.repository.RoleRepository;
import com.abixen.platform.core.application.service.AclSidService;
import com.abixen.platform.core.domain.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final AclSidService aclSidService;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,
                           AclSidService aclSidService) {
        this.roleRepository = roleRepository;
        this.aclSidService = aclSidService;
    }

    @Override
    public Role find(final Long id) {
        log.debug("find() - id: {}", id);

        return roleRepository.findOne(id);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Page<Role> findAll(final Pageable pageable, final RoleSearchForm roleSearchForm) {
        log.debug("findAll() - pageable: " + pageable);
        return roleRepository.findAll(pageable, roleSearchForm);
    }

    @Override
    public Role create(final Role role) {
        log.debug("create() - role: {}", role);

        Role createdRole = roleRepository.save(role);
        aclSidService.createAclSid(AclSidType.ROLE, createdRole.getId());

        return createdRole;
    }

    @Override
    public Role update(final Role role) {
        log.debug("update() - role: {}", role);

        return roleRepository.save(role);
    }

    @Override
    public void delete(final Long id) {
        log.debug("delete() - id: {}", id);

        try {
            roleRepository.delete(id);
        } catch (Throwable e) {
            e.printStackTrace();
            if (e.getCause() instanceof ConstraintViolationException) {
                log.warn("The role id: {} you want to remove is assigned to users.", id);
                throw new PlatformRuntimeException("The role you want to remove is assigned to users.");
            } else {
                throw e;
            }
        }
    }

}
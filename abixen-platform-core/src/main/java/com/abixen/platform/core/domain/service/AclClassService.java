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

import com.abixen.platform.common.domain.model.enumtype.AclClassName;
import com.abixen.platform.common.infrastructure.annotation.PlatformDomainService;
import com.abixen.platform.core.domain.model.AclClass;
import com.abixen.platform.core.domain.repository.AclClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@PlatformDomainService
public class AclClassService {

    private final AclClassRepository aclClassRepository;

    @Autowired
    public AclClassService(AclClassRepository aclClassRepository) {
        this.aclClassRepository = aclClassRepository;
    }

    public AclClass find(final AclClassName aclClassName) {
        log.debug("find() - aclClassName: {}", aclClassName);

        return aclClassRepository.find(aclClassName);
    }

}
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

package com.abixen.platform.service.webcontent.domain.service;

import com.abixen.platform.service.webcontent.domain.model.WebContentModuleConfig;
import com.abixen.platform.service.webcontent.domain.repository.WebContentModuleConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebContentModuleConfigurationService {

    private final WebContentModuleConfigRepository webContentModuleConfigRepository;

    @Autowired
    public WebContentModuleConfigurationService(WebContentModuleConfigRepository webContentModuleConfigRepository) {
        this.webContentModuleConfigRepository = webContentModuleConfigRepository;
    }

    public WebContentModuleConfig findByModuleId(final Long moduleId) {
        log.debug("findByModuleId() - moduleId: {}", moduleId);

        return webContentModuleConfigRepository.findByModuleId(moduleId);
    }

    public WebContentModuleConfig create(final WebContentModuleConfig webContentModuleConfig) {
        log.debug("create() - webContentModuleConfig: {}", webContentModuleConfig);

        return webContentModuleConfigRepository.save(webContentModuleConfig);
    }

    public WebContentModuleConfig update(final WebContentModuleConfig webContentModuleConfig) {
        log.debug("update() - webContentModuleConfig: {}", webContentModuleConfig);

        return webContentModuleConfigRepository.save(webContentModuleConfig);
    }

    public void deleteByModuleId(final Long moduleId) {
        log.debug("deleteByModuleId() - moduleId: {}", moduleId);

        final WebContentModuleConfig webContentModuleConfig = webContentModuleConfigRepository.findByModuleId(moduleId);

        if (webContentModuleConfig != null) {
            webContentModuleConfigRepository.delete(webContentModuleConfig);
        } else {
            log.error("Can not find webContentModuleConfig for moduleId {}", moduleId);
        }
    }
}
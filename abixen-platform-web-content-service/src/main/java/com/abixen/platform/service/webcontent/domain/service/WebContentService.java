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

import com.abixen.platform.service.webcontent.domain.model.WebContent;
import com.abixen.platform.service.webcontent.domain.repository.WebContentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class WebContentService {

    private final WebContentRepository webContentRepository;

    @Autowired
    public WebContentService(WebContentRepository webContentRepository) {
        this.webContentRepository = webContentRepository;
    }

    public WebContent find(final Long id) {
        log.debug("find() - id: {}", id);

        return webContentRepository.findOne(id);
    }

    public Page<WebContent> findAll(final Pageable pageable) {
        log.debug("findAll() - pageable: {}", pageable);

        return webContentRepository.findAll(pageable);
    }

    public WebContent create(WebContent webContent) {
        log.debug("create() - webContent: {}", webContent);

        return webContentRepository.save(webContent);
    }

    public WebContent update(WebContent webContent) {
        log.debug("update() - webContent: {}", webContent);

        return webContentRepository.save(webContent);
    }

    public void delete(final Long id) {
        log.debug("delete() - id: {}", id);

        webContentRepository.delete(id);
    }
}
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

package com.abixen.platform.service.webcontent.service.impl;

import com.abixen.platform.common.exception.PlatformRuntimeException;
import com.abixen.platform.service.webcontent.form.SearchWebContentForm;
import com.abixen.platform.service.webcontent.form.WebContentForm;
import com.abixen.platform.service.webcontent.model.impl.WebContent;
import com.abixen.platform.service.webcontent.repository.WebContentRepository;
import com.abixen.platform.service.webcontent.service.WebContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class WebContentServiceImpl implements WebContentService {

    private final WebContentRepository webContentRepository;

    @Autowired
    public WebContentServiceImpl(WebContentRepository webContentRepository) {
        this.webContentRepository = webContentRepository;
    }

    @Override
    public Page<WebContent> getWebContents(Pageable pageable) {
        log.debug("getWebContents() - pageable: {}", pageable);
        return webContentRepository.findAll(pageable);
    }

    @Override
    public Page<WebContent> getWebContents(Pageable pageable, SearchWebContentForm searchWebContentForm) {
        log.debug("getWebContents() - pageable: {}", pageable);
        return webContentRepository.findAll(pageable);
    }

    @Override
    public WebContentForm getWebContent(Long id) {
        WebContent webContent = webContentRepository.getOne(id);
        if (webContent != null) {
            return new WebContentForm(webContent);
        } else {
            throw new PlatformRuntimeException("Content not found");
        }
    }
}
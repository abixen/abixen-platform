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

import com.abixen.platform.common.exception.PlatformServiceRuntimeException;
import com.abixen.platform.service.webcontent.form.SimpleWebContentForm;
import com.abixen.platform.service.webcontent.model.enumtype.WebContentType;
import com.abixen.platform.service.webcontent.model.impl.SimpleWebContent;
import com.abixen.platform.service.webcontent.model.impl.WebContent;
import com.abixen.platform.service.webcontent.repository.WebContentRepository;
import com.abixen.platform.service.webcontent.service.SimpleWebContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Transactional
@Service
public class SimpleWebContentServiceImpl implements SimpleWebContentService {

    @Resource
    private WebContentRepository webContentRepository;

    @Override
    public SimpleWebContent createSimpleWebContent(SimpleWebContentForm simpleWebContentForm) {
        log.debug("createSimpleWebContent() - simpleWebContentForm: {}", simpleWebContentForm);
        SimpleWebContent simpleWebContent = new SimpleWebContent();
        simpleWebContent.setTitle(simpleWebContentForm.getTitle());
        simpleWebContent.setContent(simpleWebContentForm.getContent());
        simpleWebContent.setType(WebContentType.SIMPLE);
        return webContentRepository.save(simpleWebContent);
    }

    @Override
    public SimpleWebContent updateSimpleWebContent(SimpleWebContentForm simpleWebContentForm) {
        log.debug("updateSimpleWebContent() - simpleWebContentForm: {}", simpleWebContentForm);
        SimpleWebContent simpleWebContent = findSimpleWebContentById(simpleWebContentForm.getId());
        simpleWebContent.setContent(simpleWebContentForm.getContent());
        return webContentRepository.save(simpleWebContent);
    }

    @Override
    public void removeSimpleWebContent(Long simpleWebContentId) {
        log.debug("removeSimpleWebContent() - simpleWebContentId: {}", simpleWebContentId);
        webContentRepository.delete(simpleWebContentId);
    }

    @Override
    public SimpleWebContent findSimpleWebContentById(Long simpleWebContentId) {
        log.debug("findSimpleWebContentById() - simpleWebContentId: {}", simpleWebContentId);
        SimpleWebContent simpleWebContent = (SimpleWebContent) webContentRepository.findOne(simpleWebContentId);
        if (simpleWebContent == null) {
            throw new PlatformServiceRuntimeException(String.format("SimpleWebContent with id=%d not found", simpleWebContentId));
        }
        return simpleWebContent;
    }

    @Override
    public Page<SimpleWebContent> findAllSimpleWebContents(Pageable pageable) {
        log.debug("findAllSimpleWebContents() - pageable: {}", pageable);
        Page<WebContent> webContentPage = webContentRepository.findAll(pageable);

        Page<SimpleWebContent> simpleWebContentPage = new PageImpl(webContentPage.getContent(), pageable, webContentPage.getTotalElements());
        return simpleWebContentPage;
    }
}
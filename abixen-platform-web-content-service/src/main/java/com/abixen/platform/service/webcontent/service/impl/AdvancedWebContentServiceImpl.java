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

import com.abixen.platform.core.exception.PlatformServiceRuntimeException;
import com.abixen.platform.service.webcontent.form.AdvancedWebContentForm;
import com.abixen.platform.service.webcontent.model.impl.AdvancedWebContent;
import com.abixen.platform.service.webcontent.repository.AdvancedWebContentRepository;
import com.abixen.platform.service.webcontent.service.AdvancedWebContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Transactional
@Service
public class AdvancedWebContentServiceImpl implements AdvancedWebContentService {

    @Resource
    private AdvancedWebContentRepository advancedWebContentRepository;

    @Override
    public AdvancedWebContent createAdvancedWebContent(AdvancedWebContentForm advancedWebContentForm) {
        log.debug("createAdvancedWebContent() - advancedWebContentForm: {}", advancedWebContentForm);
        AdvancedWebContent advancedWebContent = new AdvancedWebContent();
        advancedWebContent.setContent(advancedWebContentForm.getContent());
        return advancedWebContentRepository.save(advancedWebContent);
    }

    @Override
    public AdvancedWebContent updateAdvancedWebContent(AdvancedWebContentForm advancedWebContentForm) {
        log.debug("updateAdvancedWebContent() - advancedWebContentForm: {}", advancedWebContentForm);
        AdvancedWebContent advancedWebContent = findAdvancedWebContentById(advancedWebContentForm.getId());
        advancedWebContent.setContent(advancedWebContentForm.getContent());
        return advancedWebContentRepository.save(advancedWebContent);
    }

    @Override
    public void removeAdvancedWebContent(Long advancedWebContentId) {
        log.debug("removeAdvancedWebContent() - advancedWebContentId: {}", advancedWebContentId);
        advancedWebContentRepository.delete(advancedWebContentId);
    }

    @Override
    public AdvancedWebContent findAdvancedWebContentById(Long advancedWebContentId) {
        log.debug("findAdvancedWebContentById() - advancedWebContentId: {}", advancedWebContentId);
        AdvancedWebContent advancedWebContent = advancedWebContentRepository.findOne(advancedWebContentId);
        if (advancedWebContent == null) {
            throw new PlatformServiceRuntimeException(String.format("AdvancedWebContent with id=%d not found", advancedWebContentId));
        }
        return advancedWebContent;
    }

    @Override
    public Page<AdvancedWebContent> findAllAdvancedWebContents(Pageable pageable) {
        log.debug("findAllAdvancedWebContents() - pageable: {}", pageable);
        return advancedWebContentRepository.findAll(pageable);
    }
}
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

package com.abixen.platform.service.webcontent.service;

import com.abixen.platform.service.webcontent.dto.WebContentDto;
import com.abixen.platform.service.webcontent.form.SearchWebContentForm;
import com.abixen.platform.service.webcontent.form.WebContentForm;
import com.abixen.platform.service.webcontent.model.impl.WebContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WebContentService {

    WebContent createWebContent(WebContentForm webContentForm);

    WebContent updateWebContent(WebContentForm advancedWebContentForm);

    WebContent findWebContent(Long id);

    WebContentDto findAndAssembleWebContent(Long id);

    Page<WebContent> getWebContents(Pageable pageable);

    Page<WebContent> getWebContents(Pageable pageable, SearchWebContentForm searchWebContentForm);

    void deleteWebContent(Long id);
}
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

package com.abixen.platform.core.service;

import com.abixen.platform.core.form.PageConfigurationForm;
import com.abixen.platform.core.form.PageForm;
import com.abixen.platform.core.model.impl.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PageService {

    Page buildPage(PageForm pageForm);

    Page createPage(Page page);

    PageForm createPage(PageForm pageForm);

    Page createPage(PageConfigurationForm pageConfigurationForm);

    Page updatePage(Page page);

    PageForm updatePage(PageForm pageForm);

    void deletePage(Long id);

    org.springframework.data.domain.Page<Page> findAllPages(Pageable pageable);

    List<Page> findAllPages();

    Page findPage(Long id);
}

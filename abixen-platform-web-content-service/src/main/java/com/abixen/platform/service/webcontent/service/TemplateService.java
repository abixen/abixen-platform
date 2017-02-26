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

import com.abixen.platform.service.webcontent.form.TemplateForm;
import com.abixen.platform.service.webcontent.model.impl.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TemplateService {

    Template createTemplate(TemplateForm templateForm);

    Template updateTemplate(TemplateForm templateForm);

    void removeTemplate(Long templateId);

    Template findTemplateById(Long templateId);

    Page<Template> findAllTemplates(Pageable pageable);

    List<Template> findAllTemplates();

    List<String> getTemplateVariables(Long id);
}

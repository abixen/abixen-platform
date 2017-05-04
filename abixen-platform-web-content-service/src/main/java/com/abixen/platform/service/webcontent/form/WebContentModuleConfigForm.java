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

package com.abixen.platform.service.webcontent.form;

import com.abixen.platform.service.webcontent.model.impl.WebContentModuleConfig;

public class WebContentModuleConfigForm {

    private Long id;
    private Long moduleId;
    private Long contentId;

    public WebContentModuleConfigForm() {
    }

    public WebContentModuleConfigForm(WebContentModuleConfig webContentModuleConfig) {
        this.id = webContentModuleConfig.getId();
        this.moduleId = webContentModuleConfig.getModuleId();
        this.contentId = webContentModuleConfig.getWebContent().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
}
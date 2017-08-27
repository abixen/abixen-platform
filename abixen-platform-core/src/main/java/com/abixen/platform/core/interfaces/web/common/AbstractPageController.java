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

package com.abixen.platform.core.interfaces.web.common;

import com.abixen.platform.core.application.service.PageManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Slf4j
public abstract class AbstractPageController {

    private final PageManagementService pageManagementService;

    public AbstractPageController(PageManagementService pageManagementService) {
        this.pageManagementService = pageManagementService;
    }


    @PreAuthorize("hasPermission(#id, 'com.abixen.platform.core.domain.model.impl.Page', 'PAGE_DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
        log.debug("delete() - id: {}", id);
        pageManagementService.deletePage(id);

        return new ResponseEntity(Boolean.TRUE, HttpStatus.OK);
    }

}
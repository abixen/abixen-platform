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

package com.abixen.platform.core.controller.admin;

import com.abixen.platform.core.model.impl.Theme;
import com.abixen.platform.core.service.ThemeService;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/admin/themes")
public class AdminThemeController {

    private final Logger log = Logger.getLogger(AdminThemeController.class.getName());

    private static final int PAGEABLE_DEFAULT_PAGE_SIZE = 100;

    private final ThemeService themeService;

    @Autowired
    public AdminThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public org.springframework.data.domain.Page<Theme> getThemes(@PageableDefault(size = PAGEABLE_DEFAULT_PAGE_SIZE) Pageable pageable) {
        log.debug("getThemes()");
        org.springframework.data.domain.Page<Theme> themes = themeService.findAllThemes(pageable);
        for (Theme theme : themes) {
            log.debug("theme: " + theme);
        }
        return themes;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Theme uploadTheme(@RequestParam("file") MultipartFile file) throws IOException {
        return themeService.uploadTheme(file);
    }
}
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

package com.abixen.platform.module.magicnumber.controller;

import com.abixen.platform.core.security.PlatformWebUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/application/modules/abixen/magic-number/init")
public class MagicNumberModuleInitController {

    private final Logger log = LoggerFactory.getLogger(MagicNumberModuleInitController.class.getName());

    @PreAuthorize("hasPermission(#id, 'Module', 'MODULE_VIEW')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PlatformWebUser init(@PathVariable Long id) {
        log.debug("init() - id:" + id);

        //TODO - temporary for testing frontend loaders.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (PlatformWebUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}

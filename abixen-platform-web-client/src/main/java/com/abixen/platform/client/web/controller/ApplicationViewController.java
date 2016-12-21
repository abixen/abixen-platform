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

package com.abixen.platform.client.web.controller;

import com.abixen.platform.client.web.client.ResourceClient;
import com.abixen.platform.client.web.model.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("")
public class ApplicationViewController {

    @Autowired
    ResourceClient resourceClient;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView renderApplicationPage() {
        log.debug("renderApplicationPage()");

        List<Resource> resources = resourceClient.getAllUniqueResources();

        resources.forEach(resource -> log.debug("resource: " + resource));

        ModelAndView modelAndView = new ModelAndView("application/index");
        modelAndView.addObject("resources", resources);

        return modelAndView;

    }
}

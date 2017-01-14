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

package com.abixen.platform.core.controller;

import com.abixen.platform.core.dto.FormErrorDto;
import com.abixen.platform.core.dto.FormValidationResultDto;
import com.abixen.platform.core.form.LayoutForm;
import com.abixen.platform.core.model.impl.Layout;
import com.abixen.platform.core.service.LayoutService;
import lombok.extern.slf4j.Slf4j;
import com.abixen.platform.core.util.ValidationUtil;
import com.abixen.platform.core.util.WebModelJsonSerialize;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


//FIXME
@Slf4j
@RestController
public class LayoutController {

    @Autowired
    private LayoutService layoutService;

    //FIXME
    @RequestMapping(value = "/api/dashboard", method = RequestMethod.GET)
    public Page<Layout> getDashboardLayouts(@PageableDefault(size = 1, page = 0) Pageable pageable) {
        log.debug("getLayouts()");

        Page<Layout> layouts = layoutService.findAllLayouts(pageable);

        for (Layout layout : layouts) {
            log.debug("layout: " + layout);

            String html = layout.getContent();
            layout.setContent(layoutService.htmlLayoutToJson(html));
        }
        return layouts;
    }

    //FIXME
    @RequestMapping(value = "/api/admin/layouts", method = RequestMethod.GET)
    public Page<Layout> getLayouts(@PageableDefault(size = 1, page = 0) Pageable pageable) {
        log.debug("getLayouts()");

        Page<Layout> layouts = layoutService.findAllLayouts(pageable);
        for (Layout layout : layouts) {
            log.debug("layout: " + layout);
        }

        return layouts;
    }

    //FIXME
    @RequestMapping(value = "/api/admin/layouts/{id}", method = RequestMethod.GET)
    public Layout getLayout(@PathVariable Long id) {
        log.debug("getLayout() - id: " + id);

        Layout layout = layoutService.findLayout(id);

        return layout;
    }


    @RequestMapping(value = "/api/admin/layouts", method = RequestMethod.POST)
    public Layout createLayout(@RequestBody Layout layout) {
        log.debug("save() - layout: " + layout);
        return layoutService.createLayout(layout);
    }

    //FIXME
    @JsonView(WebModelJsonSerialize.class)
    @RequestMapping(value = "/api/admin/layouts/{id}", method = RequestMethod.PUT)
    FormValidationResultDto updateLayout(@PathVariable Long id, @RequestBody @Valid LayoutForm layoutForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(layoutForm, formErrors);
        }
        LayoutForm layoutFormResult = layoutService.updateLayout(layoutForm);
        return new FormValidationResultDto(layoutFormResult);

    }

    //FIXME
    @RequestMapping(value = "/api/admin/layouts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteLayout(@PathVariable("id") long id) {
        log.debug("delete() - id: " + id);
        layoutService.deleteLayout(id);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/admin/layouts/{id}/icon", method = RequestMethod.POST)
    public Layout updateLayoutIcon(@PathVariable Long id, @RequestParam("iconFile") MultipartFile iconFile) throws IOException {
        return layoutService.changeIcon(id, iconFile);
    }
}

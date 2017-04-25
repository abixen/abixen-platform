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

import com.abixen.platform.core.converter.LayoutToLayoutDtoConverter;
import com.abixen.platform.common.dto.FormErrorDto;
import com.abixen.platform.common.dto.FormValidationResultDto;
import com.abixen.platform.core.dto.LayoutDto;
import com.abixen.platform.core.form.LayoutForm;
import com.abixen.platform.core.form.LayoutSearchForm;
import com.abixen.platform.core.model.impl.Layout;
import com.abixen.platform.core.service.LayoutService;
import com.abixen.platform.common.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@RestController
@RequestMapping(value = "/api/control-panel/layouts")
public class AdminLayoutController {

    private final LayoutService layoutService;
    private final LayoutToLayoutDtoConverter layoutToLayoutDtoConverter;

    @Autowired
    public AdminLayoutController(LayoutService layoutService,
                                 LayoutToLayoutDtoConverter layoutToLayoutDtoConverter) {
        this.layoutService = layoutService;
        this.layoutToLayoutDtoConverter = layoutToLayoutDtoConverter;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<LayoutDto> getLayouts(@PageableDefault(size = 1) Pageable pageable, LayoutSearchForm layoutSearchForm) {
        log.debug("getLayouts()");

        Page<Layout> layouts = layoutService.findAllLayouts(pageable, layoutSearchForm);

        for (Layout layout : layouts) {
            String html = layout.getContent();
            layout.setContent(layoutService.htmlLayoutToJson(html));
        }

        Page<LayoutDto> layoutDtos = layoutToLayoutDtoConverter.convertToPage(layouts);

        return layoutDtos;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public LayoutDto getLayout(@PathVariable Long id) {
        log.debug("getLayout() - id: {}", id);

        Layout layout = layoutService.findLayout(id);
        return layoutToLayoutDtoConverter.convert(layout);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public FormValidationResultDto createLayout(@RequestBody Layout layout) {
        log.debug("save() - layout: {}", layout);
        Layout createdLayout = layoutService.createLayout(layout);
        return new FormValidationResultDto(new LayoutForm(createdLayout));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    FormValidationResultDto updateLayout(@PathVariable Long id, @RequestBody @Valid LayoutForm layoutForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FormErrorDto> formErrors = ValidationUtil.extractFormErrors(bindingResult);
            return new FormValidationResultDto(layoutForm, formErrors);
        }
        LayoutForm layoutFormResult = layoutService.updateLayout(layoutForm);
        return new FormValidationResultDto(layoutFormResult);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteLayout(@PathVariable("id") long id) {
        log.debug("delete() - id: {}", id);
        layoutService.deleteLayout(id);
        return new ResponseEntity(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/icon", method = RequestMethod.POST)
    public LayoutDto updateLayoutIcon(@PathVariable Long id, @RequestParam("iconFile") MultipartFile iconFile) throws IOException {
        Layout layout = layoutService.changeIcon(id, iconFile);
        return layoutToLayoutDtoConverter.convert(layout);
    }
}
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

package com.abixen.platform.core.application.service;

import com.abixen.platform.core.application.dto.LayoutDto;
import com.abixen.platform.core.application.form.LayoutForm;
import com.abixen.platform.core.application.form.LayoutSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface LayoutManagementService {

    LayoutDto findLayout(Long id);

    List<LayoutDto> findAllLayouts();

    Page<LayoutDto> findAllLayouts(Pageable pageable, LayoutSearchForm layoutSearchForm);

    LayoutForm createLayout(LayoutForm layoutForm);

    LayoutForm updateLayout(LayoutForm layoutForm);

    void deleteLayout(Long id);

    LayoutDto changeLayoutIcon(Long id, MultipartFile iconFile) throws IOException;

}
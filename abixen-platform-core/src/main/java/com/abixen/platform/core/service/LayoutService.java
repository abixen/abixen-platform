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

import com.abixen.platform.core.model.impl.Layout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


public interface LayoutService {

    Layout createLayout(Layout layout);

    Layout updateLayout(Layout layout);

    void deleteLayout(Long id);

    String htmlLayoutToJson(String htmlString);

    Page<Layout> findAllLayouts(Pageable pageable);

    Layout findLayout(Long id);

    Layout changeIcon(Long id, MultipartFile iconFile) throws IOException;

}

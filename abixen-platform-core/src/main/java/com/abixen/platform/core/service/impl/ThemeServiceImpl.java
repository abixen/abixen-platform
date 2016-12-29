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

package com.abixen.platform.core.service.impl;

import com.abixen.platform.core.model.impl.Theme;
import com.abixen.platform.core.repository.ThemeRepository;
import com.abixen.platform.core.service.*;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


@Transactional
@Service
public class ThemeServiceImpl implements ThemeService {

    private static Logger log = Logger.getLogger(ThemeServiceImpl.class.getName());

    @Resource
    private ThemeRepository themeRepository;

    @Override
    public org.springframework.data.domain.Page<Theme> findAllThemes(Pageable pageable) {
        log.debug("findAllThemes() - pageable: " + pageable);
        return themeRepository.findAll(pageable);
    }
}
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

package com.abixen.platform.core.model;


public interface PageBase<Layout extends LayoutBase> {

    int PAGE_NAME_MIN_LENGTH = 5;
    int PAGE_NAME_MAX_LENGTH = 20;

    int PAGE_TITLE_MIN_LENGTH = 6;
    int PAGE_TITLE_MAX_LENGTH = 40;

    int PAGE_DESCRIPTION_MIN_LENGTH = 40;
    int PAGE_DESCRIPTION_MAX_LENGTH = 40;


    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    Layout getLayout();

    void setLayout(Layout layout);
}

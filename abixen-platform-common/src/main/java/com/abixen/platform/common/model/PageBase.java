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

package com.abixen.platform.common.model;


public interface PageBase<Layout extends LayoutBase> {

    int PAGE_TITLE_MAX_LENGTH = 40;
    int PAGE_ICON_MAX_LENGTH = 40;
    int PAGE_DESCRIPTION_MAX_LENGTH = 255;


    Long getId();

    void setId(Long id);

    String getTitle();

    void setTitle(String title);

    String getIcon();

    void setIcon(String icon);

    String getDescription();

    void setDescription(String description);

    Layout getLayout();

    void setLayout(Layout layout);
}

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


public interface LayoutBase {

   int LAYOUT_TITLE_MAX_LENGTH = 40;
   int LAYOUT_CONTENT_MAX_LENGTH = 4000;
   int LAYOUT_ICON_FILE_NAME_MAX_LENGTH = 100;

    Long getId();

    void setId(Long id);

    String getContent();

    void setContent(String content);

    String getTitle();

    void setTitle(String title);

    String getIconFileName();

    void setIconFileName(String iconFileName);

    String getContentAsJson();

    void setContentAsJson(String contentAsJson);
}

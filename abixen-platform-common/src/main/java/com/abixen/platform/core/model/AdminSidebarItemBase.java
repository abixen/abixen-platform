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


public interface AdminSidebarItemBase{

    int TITLE_MAX_LENGTH = 40;
    int ANGULAR_JS_STATE_MAX_LENGTH = 255;
    int ICON_CLASS_MAX_LENGTH = 40;

    Long getId();

    void setId(Long id);

    String getTitle();

    void setTitle(String title);

    String getAngularJsState();

    void setAngularJsState(String angularJsState);

    Double getOrderIndex();

    void setOrderIndex(Double orderIndex);

    String getIconClass();

    void setIconClass(String iconClass);
}
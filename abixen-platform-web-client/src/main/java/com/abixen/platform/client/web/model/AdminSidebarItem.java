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

package com.abixen.platform.client.web.model;


import com.abixen.platform.common.model.AdminSidebarItemBase;
import com.abixen.platform.common.model.Model;

public class AdminSidebarItem extends Model implements AdminSidebarItemBase {

    private Long id;
    private String title;
    private String angularJsState;
    private Double orderIndex;
    private String iconClass;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getAngularJsState() {
        return angularJsState;
    }

    @Override
    public void setAngularJsState(String angularJsState) {
        this.angularJsState = angularJsState;
    }

    @Override
    public Double getOrderIndex() {
        return orderIndex;
    }

    @Override
    public void setOrderIndex(Double orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Override
    public String getIconClass() {
        return iconClass;
    }

    @Override
    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

}
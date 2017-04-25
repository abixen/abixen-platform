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

package com.abixen.platform.core.model.impl;

import com.abixen.platform.common.model.AdminSidebarItemBase;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;


@JsonSerialize(as = AdminSidebarItem.class)
@Entity
@Table(name = "admin_sidebar_item")
@SequenceGenerator(sequenceName = "admin_sidebar_item_seq", name = "admin_sidebar_item_seq", allocationSize = 1)
public class AdminSidebarItem extends AuditingModel implements AdminSidebarItemBase {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "admin_sidebar_item_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", length = TITLE_MAX_LENGTH, nullable = false)
    private String title;

    @Column(name = "angular_js_state", length = ANGULAR_JS_STATE_MAX_LENGTH, nullable = false)
    private String angularJsState;

    @Column(name = "order_index", nullable = false)
    private Double orderIndex;

    @Column(name = "icon_class", length = ICON_CLASS_MAX_LENGTH, nullable = false)
    private String iconClass;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
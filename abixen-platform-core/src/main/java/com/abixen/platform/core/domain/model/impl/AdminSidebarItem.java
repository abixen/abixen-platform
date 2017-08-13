/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.core.domain.model.impl;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@JsonSerialize(as = AdminSidebarItem.class)
@Entity
@Table(name = "admin_sidebar_item")
@SequenceGenerator(sequenceName = "admin_sidebar_item_seq", name = "admin_sidebar_item_seq", allocationSize = 1)
public class AdminSidebarItem extends AuditingModel {

    public static final int TITLE_MAX_LENGTH = 40;
    public static final int ANGULAR_JS_STATE_MAX_LENGTH = 255;
    public static final int ICON_CLASS_MAX_LENGTH = 40;

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAngularJsState() {
        return angularJsState;
    }

    public void setAngularJsState(String angularJsState) {
        this.angularJsState = angularJsState;
    }

    public Double getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Double orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

}
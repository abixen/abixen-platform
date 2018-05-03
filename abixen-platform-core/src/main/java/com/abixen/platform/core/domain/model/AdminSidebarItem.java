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

package com.abixen.platform.core.domain.model;

import com.abixen.platform.common.domain.model.EntityBuilder;
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
public final class AdminSidebarItem extends AuditingModel {

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

    private AdminSidebarItem() {
    }

    @Override
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getAngularJsState() {
        return angularJsState;
    }

    private void setAngularJsState(String angularJsState) {
        this.angularJsState = angularJsState;
    }

    public Double getOrderIndex() {
        return orderIndex;
    }

    private void setOrderIndex(Double orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getIconClass() {
        return iconClass;
    }

    private void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<AdminSidebarItem> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new AdminSidebarItem();
        }

        public Builder title(String title) {
            this.product.setTitle(title);
            return this;
        }

        public Builder angularJsState(String angularJsState) {
            this.product.setAngularJsState(angularJsState);
            return this;
        }

        public Builder orderIndex(Double orderIndex) {
            this.product.setOrderIndex(orderIndex);
            return this;
        }

        public Builder iconClass(String iconClass) {
            this.product.setIconClass(iconClass);
            return this;
        }

    }
}
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "module")
@SequenceGenerator(sequenceName = "module_seq", name = "module_seq", allocationSize = 1)
public final class Module extends AuditingModel implements SecurableModel {

    public static final int MODULE_TITLE_MIN_LENGTH = 6;
    public static final int MODULE_TITLE_MAX_LENGTH = 40;
    public static final int MODULE_DESCRIPTION_MAX_LENGTH = 100;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "module_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", length = MODULE_TITLE_MAX_LENGTH, nullable = false)
    private String title;

    @Column(name = "description", length = MODULE_DESCRIPTION_MAX_LENGTH)
    private String description;

    @JoinColumn(name = "module_type_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private ModuleType moduleType;

    @JoinColumn(name = "page_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Page page;

    @Column(name = "row_index", nullable = false)
    private Integer rowIndex;

    @Column(name = "column_index", nullable = false)
    private Integer columnIndex;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    private Module() {
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

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    private void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    public Page getPage() {
        return page;
    }

    private void setPage(Page page) {
        this.page = page;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    private void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    private void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    private void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public void changeTitle(String title) {
        setTitle(title);
    }

    public void changeDescription(String description) {
        setDescription(description);
    }

    public void changePositionIndexes(Integer rowIndex, Integer columnIndex, Integer orderIndex) {
        setRowIndex(rowIndex);
        setColumnIndex(columnIndex);
        setOrderIndex(orderIndex);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends EntityBuilder<Module> {

        private Builder() {
        }

        @Override
        protected void initProduct() {
            this.product = new Module();
        }

        public Builder title(String title) {
            this.product.setTitle(title);
            return this;
        }

        public Builder moduleType(ModuleType moduleType) {
            this.product.setModuleType(moduleType);
            return this;
        }

        public Builder page(Page page) {
            this.product.setPage(page);
            return this;
        }

        public Builder description(String description) {
            this.product.setDescription(description);
            return this;
        }

        public Builder positionIndexes(Integer rowIndex, Integer columnIndex, Integer orderIndex) {
            this.product.setRowIndex(rowIndex);
            this.product.setColumnIndex(columnIndex);
            this.product.setOrderIndex(orderIndex);
            return this;
        }
    }
}
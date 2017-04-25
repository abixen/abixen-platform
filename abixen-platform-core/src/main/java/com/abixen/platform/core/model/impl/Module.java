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

import com.abixen.platform.common.model.ModuleBase;
import com.abixen.platform.common.model.SecurableModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;


@JsonSerialize(as = Module.class)
@Entity
@Table(name = "module")
@SequenceGenerator(sequenceName = "module_seq", name = "module_seq", allocationSize = 1)
public class Module extends AuditingModel implements ModuleBase<ModuleType, Page>, SecurableModel<User> {

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
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public ModuleType getModuleType() {
        return moduleType;
    }

    @Override
    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public Integer getRowIndex() {
        return rowIndex;
    }

    @Override
    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    @Override
    public Integer getColumnIndex() {
        return columnIndex;
    }

    @Override
    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    @Override
    public Integer getOrderIndex() {
        return orderIndex;
    }

    @Override
    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}

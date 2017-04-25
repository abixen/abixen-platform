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

package com.abixen.platform.core.dto;


import javax.validation.constraints.NotNull;


public class DashboardModuleDto {

    private Long id;

    private String description;

    @NotNull
    private String type;

    @NotNull
    private ModuleTypeDto moduleType;

    @NotNull
    private String title;

    @NotNull
    private Integer rowIndex;

    @NotNull
    private Integer columnIndex;

    @NotNull
    private Integer orderIndex;

    @NotNull
    private Long frontendId;

    public DashboardModuleDto() {
    }

    public DashboardModuleDto(Long id, String description, String type, ModuleTypeDto moduleType, String title, Integer rowIndex, Integer columnIndex, Integer orderIndex) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.moduleType = moduleType;
        this.title = title;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.orderIndex = orderIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ModuleTypeDto getModuleType() {
        return moduleType;
    }

    public void setModuleType(ModuleTypeDto moduleType) {
        this.moduleType = moduleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Long getFrontendId() {
        return frontendId;
    }

    public void setFrontendId(Long frontendId) {
        this.frontendId = frontendId;
    }

    @Override
    public String toString() {
        return "DashboardModuleDto{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", moduleType=" + moduleType +
                ", title='" + title + '\'' +
                ", rowIndex=" + rowIndex +
                ", columnIndex=" + columnIndex +
                ", orderIndex=" + orderIndex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DashboardModuleDto that = (DashboardModuleDto) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

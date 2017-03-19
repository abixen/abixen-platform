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


public interface ModuleBase<ModuleType extends ModuleTypeBase, Page extends PageBase> {

    int MODULE_TITLE_MIN_LENGTH = 6;
    int MODULE_TITLE_MAX_LENGTH = 40;

    int MODULE_DESCRIPTION_MAX_LENGTH = 100;

    Long getId();

    void setId(Long id);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    ModuleType getModuleType();

    void setModuleType(ModuleType moduleType);

    Page getPage();

    void setPage(Page page);

    Integer getRowIndex();

    void setRowIndex(Integer rowIndex);

    Integer getColumnIndex();

    void setColumnIndex(Integer columnIndex);

    Integer getOrderIndex();

    void setOrderIndex(Integer orderIndex);

}

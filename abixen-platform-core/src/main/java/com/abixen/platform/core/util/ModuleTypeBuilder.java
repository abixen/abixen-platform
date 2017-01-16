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

package com.abixen.platform.core.util;

import com.abixen.platform.core.model.impl.AdminSidebarItem;
import com.abixen.platform.core.model.impl.ModuleType;

import java.util.List;


public interface ModuleTypeBuilder {

    ModuleType build();

    ModuleTypeBuilder basic(String name, String title, String description);

    ModuleTypeBuilder angular(String angularJsNameApplication, String angularJsNameAdmin);

    ModuleTypeBuilder initUrl(String initUrl);

    ModuleTypeBuilder serviceId(String serviceId);

    ModuleTypeBuilder adminSidebarItems(List<AdminSidebarItem> adminSidebarItems);

}
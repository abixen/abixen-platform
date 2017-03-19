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


import java.util.List;

public interface ModuleTypeBase<AdminSidebarItem extends AdminSidebarItemBase, Resource extends ResourceBase> {

    int MODULETYPE_NAME_MIN_LENGTH = 5;
    int MODULETYPE_NAME_MAX_LENGTH = 20;

    int MODULETYPE_ANGULAR_JS_NAME_MAX_LENGTH = 100;

    int MODULETYPE_TITLE_MIN_LENGTH = 6;
    int MODULETYPE_TITLE_MAX_LENGTH = 40;

    int MODULETYPE_DESCRIPTION_MIN_LENGTH = 40;
    int MODULETYPE_DESCRIPTION_MAX_LENGTH = 40;


    int RESOURCE_SERVICE_ID_MIN_LENGTH = 1;
    int RESOURCE_SERVICE_ID_MAX_LENGTH = 255;


    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getAngularJsNameApplication();

    void setAngularJsNameApplication(String angularJsNameApplication);

    String getAngularJsNameAdmin();

    void setAngularJsNameAdmin(String angularJsNameAdmin);

    String getTitle();

    void setTitle(String title);

    String getDescription();

    void setDescription(String description);

    String getInitUrl();

    void setInitUrl(String initUrl);

    String getServiceId();

    void setServiceId(String serviceId);

    List<Resource> getResources();

    void setResources(List<Resource> resources);

    List<AdminSidebarItem> getAdminSidebarItems();

    void setAdminSidebarItems(List<AdminSidebarItem> adminSidebarItems);

}
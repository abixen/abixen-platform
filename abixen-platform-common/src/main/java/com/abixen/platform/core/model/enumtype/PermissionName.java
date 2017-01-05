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

package com.abixen.platform.core.model.enumtype;


public enum PermissionName {
    PAGE_VIEW("PAGE_VIEW"),
    PAGE_ADD("PAGE_ADD"),
    PAGE_EDIT("PAGE_EDIT"),
    PAGE_DELETE("PAGE_DELETE"),
    PAGE_CONFIGURATION("PAGE_CONFIGURATION"),
    PAGE_PERMISSION("PAGE_PERMISSION"),

    MODULE_VIEW("MODULE_VIEW"),
    MODULE_ADD("MODULE_ADD"),
    MODULE_EDIT("MODULE_EDIT"),
    MODULE_DELETE("MODULE_DELETE"),
    MODULE_CONFIGURATION("MODULE_CONFIGURATION"),
    MODULE_PERMISSION("MODULE_PERMISSION"),

    USERS_VIEW("USERS_VIEW"),
    USERS_ADD("USERS_ADD"),
    USERS_EDIT("USERS_EDIT"),
    USERS_DELETE("USERS_DELETE"),

    ROLES_VIEW("ROLES_VIEW"),
    ROLES_ADD("ROLES_ADD"),
    ROLES_EDIT("ROLES_EDIT"),
    ROLES_DELETE("ROLES_DELETE"),

    LAYOUT_VIEW("LAYOUT_VIEW"),
    LAYOUT_ADD("LAYOUT_ADD"),
    LAYOUT_EDIT("LAYOUT_EDIT"),
    LAYOUT_DELETE("LAYOUT_DELETE"),
    LAYOUT_PERMISSION("LAYOUT_PERMISSION"),

    MODULE_TYPE_VIEW("MODULE_TYPE_VIEW"),
    MODULE_TYPE_ADD("MODULE_TYPE_ADD"),
    MODULE_TYPE_PERMISSION("MODULE_TYPE_PERMISSION");


    private final String name;

    public String getName() {
        return name;
    }

    private PermissionName(String name) {
        this.name = name;
    }
}

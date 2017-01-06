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
    PAGE_VIEW(Values.PAGE_VIEW),
    PAGE_ADD(Values.PAGE_ADD),
    PAGE_EDIT(Values.PAGE_EDIT),
    PAGE_DELETE(Values.PAGE_DELETE),
    PAGE_CONFIGURATION(Values.PAGE_CONFIGURATION),
    PAGE_PERMISSION(Values.PAGE_PERMISSION),

    MODULE_VIEW(Values.MODULE_VIEW),
    MODULE_ADD(Values.MODULE_ADD),
    MODULE_EDIT(Values.MODULE_EDIT),
    MODULE_DELETE(Values.MODULE_DELETE),
    MODULE_CONFIGURATION(Values.MODULE_CONFIGURATION),
    MODULE_PERMISSION(Values.MODULE_PERMISSION),

    USERS_VIEW(Values.USERS_VIEW),
    USERS_ADD(Values.USERS_ADD),
    USERS_EDIT(Values.USERS_EDIT),
    USERS_DELETE(Values.USERS_DELETE),

    ROLES_VIEW(Values.ROLES_VIEW),
    ROLES_ADD(Values.ROLES_ADD),
    ROLES_EDIT(Values.ROLES_EDIT),
    ROLES_DELETE(Values.ROLES_DELETE),

    LAYOUT_VIEW(Values.LAYOUT_VIEW),
    LAYOUT_ADD(Values.LAYOUT_ADD),
    LAYOUT_EDIT(Values.LAYOUT_EDIT),
    LAYOUT_DELETE(Values.LAYOUT_DELETE),
    LAYOUT_PERMISSION(Values.LAYOUT_PERMISSION),

    MODULE_TYPE_VIEW(Values.MODULE_TYPE_VIEW),
    MODULE_TYPE_ADD(Values.MODULE_TYPE_ADD),
    MODULE_TYPE_PERMISSION(Values.MODULE_TYPE_PERMISSION);


    private final String name;

    public String getName() {
        return name;
    }

    private PermissionName(String name) {
        this.name = name;
    }

    public static class Values {
        public static final String PAGE_VIEW = "PAGE_VIEW";
        public static final String PAGE_ADD = "PAGE_ADD";
        public static final String PAGE_EDIT = "PAGE_EDIT";
        public static final String PAGE_DELETE = "PAGE_DELETE";
        public static final String PAGE_CONFIGURATION = "PAGE_CONFIGURATION";
        public static final String PAGE_PERMISSION = "PAGE_PERMISSION";

        public static final String MODULE_VIEW = "MODULE_VIEW";
        public static final String MODULE_ADD = "MODULE_ADD";
        public static final String MODULE_EDIT = "MODULE_EDIT";
        public static final String MODULE_DELETE = "MODULE_DELETE";
        public static final String MODULE_CONFIGURATION = "MODULE_CONFIGURATION";
        public static final String MODULE_PERMISSION = "MODULE_PERMISSION";

        public static final String USERS_VIEW = "USERS_VIEW";
        public static final String USERS_ADD = "USERS_ADD";
        public static final String USERS_EDIT = "USERS_EDIT";
        public static final String USERS_DELETE = "USERS_DELETE";

        public static final String ROLES_VIEW = "ROLES_VIEW";
        public static final String ROLES_ADD = "ROLES_ADD";
        public static final String ROLES_EDIT = "ROLES_EDIT";
        public static final String ROLES_DELETE = "ROLES_DELETE";

        public static final String LAYOUT_VIEW = "LAYOUT_VIEW";
        public static final String LAYOUT_ADD = "LAYOUT_ADD";
        public static final String LAYOUT_EDIT = "LAYOUT_EDIT";
        public static final String LAYOUT_DELETE = "LAYOUT_DELETE";
        public static final String LAYOUT_PERMISSION = "LAYOUT_PERMISSION";

        public static final String MODULE_TYPE_VIEW = "MODULE_TYPE_VIEW";
        public static final String MODULE_TYPE_ADD = "MODULE_TYPE_ADD";
        public static final String MODULE_TYPE_PERMISSION = "MODULE_TYPE_PERMISSION";
    }
}
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

package com.abixen.platform.common.model.enumtype;


public enum AclClassName {
    LAYOUT(Values.LAYOUT),
    MODULE(Values.MODULE),
    PAGE(Values.PAGE),
    ROLE(Values.ROLE),
    USER(Values.USER),
    MODULE_TYPE(Values.MODULE_TYPE);

    private final String name;

    public String getName() {
        return name;
    }

    AclClassName(String name) {
        this.name = name;
    }

    public static AclClassName getByName(String name) {
        for (AclClassName prop : values()) {
            if (prop.getName().equals(name)) {
                return prop;
            }
        }

        throw new IllegalArgumentException(name + " is not a valid AclClassName");
    }

    public static class Values {
        public static final String LAYOUT = "com.abixen.platform.core.model.impl.Layout";
        public static final String MODULE = "com.abixen.platform.core.model.impl.Module";
        public static final String PAGE = "com.abixen.platform.core.model.impl.Page";
        public static final String ROLE = "com.abixen.platform.core.model.impl.Role";
        public static final String USER = "com.abixen.platform.core.model.impl.User";
        public static final String MODULE_TYPE = "com.abixen.platform.core.model.impl.ModuleType";
    }
}
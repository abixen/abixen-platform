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

package com.abixen.platform.common.configuration.properties;


import com.abixen.platform.common.model.enumtype.ResourcePage;
import com.abixen.platform.common.model.enumtype.ResourcePageLocation;
import com.abixen.platform.common.model.enumtype.ResourceType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class AbstractModulesConfigurationProperties {

    @NotNull
    private List<Module> modules = new ArrayList<>();

    @NotNull
    private List<Module.AdminSidebarItem> adminSidebarItems = new ArrayList<>();

    @Getter
    @Setter
    public static class Module {

        @NotNull
        private String name;

        private String angularJsNameApplication;

        private String angularJsNameAdmin;

        @NotNull
        private String title;

        @NotNull
        private String description;

        @NotNull
        private String relativeInitUrl;

        @NotNull
        private List<StaticResource> staticResources = new ArrayList<>();

        @Getter
        @Setter
        public static class StaticResource {

            @NotNull
            private String relativeUrl;

            @NotNull
            private ResourcePageLocation resourcePageLocation;

            @NotNull
            private ResourcePage resourcePage;

            @NotNull
            private ResourceType resourceType;
        }

        @Getter
        @Setter
        public static class AdminSidebarItem {

            @NotNull
            private String title;

            @NotNull
            private String angularJsState;

            @NotNull
            private Double orderIndex;

            @NotNull
            private String iconClass;

            @NotNull
            private List<Module> modules = new ArrayList<>();

        }
    }
}
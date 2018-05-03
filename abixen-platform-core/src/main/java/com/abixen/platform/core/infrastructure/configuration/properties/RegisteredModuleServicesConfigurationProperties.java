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

package com.abixen.platform.core.infrastructure.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Component
@EnableConfigurationProperties(RegisteredModuleServicesConfigurationProperties.class)
@ConfigurationProperties(prefix = "platform.core.modules")
public class RegisteredModuleServicesConfigurationProperties {

    @NotNull
    private List<RegisteredModuleServicesConfigurationProperties.Service> registeredServices = new ArrayList<>();

    @Getter
    @Setter
    public static class Service {

        @NotNull
        private String serviceId;

    }
}
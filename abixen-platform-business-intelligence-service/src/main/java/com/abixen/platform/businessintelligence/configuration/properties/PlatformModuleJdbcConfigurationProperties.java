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

package com.abixen.platform.businessintelligence.configuration.properties;


import com.abixen.platform.core.configuration.properties.AbstractPlatformJdbcConfigurationProperties;
import com.abixen.platform.core.util.PlatformProfiles;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@Profile({PlatformProfiles.DEV, PlatformProfiles.CLOUD})
@ConfigurationProperties(prefix = "platform.businessintelligence.jdbc")
public class PlatformModuleJdbcConfigurationProperties extends AbstractPlatformJdbcConfigurationProperties {

}

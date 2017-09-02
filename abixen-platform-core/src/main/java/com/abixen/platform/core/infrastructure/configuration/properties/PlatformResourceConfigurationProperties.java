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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


import static com.abixen.platform.common.infrastructure.util.PlatformProfiles.DOCKER;
import static com.abixen.platform.common.infrastructure.util.PlatformProfiles.DEV;

@Profile({DEV, DOCKER})
@Component
@EnableConfigurationProperties(PlatformResourceConfigurationProperties.class)
@ConfigurationProperties(prefix = "platform.core.resource", locations = {"bootstrap.yml"})
public class PlatformResourceConfigurationProperties extends AbstractPlatformResourceConfigurationProperties {

}

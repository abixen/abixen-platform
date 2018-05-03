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
package com.abixen.platform.core.infrastructure.configuration;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import static com.abixen.platform.common.infrastructure.util.PlatformProfiles.DEV;
import static com.abixen.platform.common.infrastructure.util.PlatformProfiles.DOCKER;

@Profile({DEV, DOCKER})
@Configuration
@EnableRetry
@EnableRedisHttpSession
@EnableEurekaClient
@EnableCircuitBreaker
public class CoreCloudIntegrationConfiguration {
}
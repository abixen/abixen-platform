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

package com.abixen.platform.service.webcontent;

import com.abixen.platform.service.webcontent.infrastructure.configuration.WebContentServicePackages;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRetry
@SpringBootApplication
@EnableRedisHttpSession
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {WebContentServicePackages.CLIENT})
public class WebContentServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebContentServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebContentServiceApplication.class, args);
    }

}
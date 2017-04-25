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

package com.abixen.platform.core.integration;

import com.abixen.platform.core.client.ModuleConfigurationService;
import com.abixen.platform.core.client.ModulesConfigurationProperties;
import com.abixen.platform.core.exception.PlatformCoreException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ModuleConfigurationIntegrationClient {

    @Autowired(required = false)
    private DiscoveryClient discoveryClient;

    @HystrixCommand(fallbackMethod = "getModulesConfigurationPropertiesFallback")
    public ModulesConfigurationProperties getModulesConfigurationProperties(String serviceName) {
        log.debug("getModulesConfigurationProperties: " + serviceName);

        if (discoveryClient.getInstances(serviceName).isEmpty()) {
            throw new PlatformCoreException("Can not find any instance for " + serviceName);
        }

        ServiceInstance serviceInstance = discoveryClient.getInstances(serviceName).get(0);
        ModuleConfigurationService moduleConfigurationService = Feign.builder().contract(new JAXRSContract()).encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder()).target(ModuleConfigurationService.class,
                        "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort());

        return moduleConfigurationService.getModulesConfigurationProperties();
    }

    private ModulesConfigurationProperties getModulesConfigurationPropertiesFallback(String serviceName, Throwable e) {
        log.error("getModulesConfigurationPropertiesFallback: " + serviceName);
        e.printStackTrace();
        return null;
    }
}
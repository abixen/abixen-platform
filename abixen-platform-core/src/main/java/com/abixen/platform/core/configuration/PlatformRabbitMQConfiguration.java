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

package com.abixen.platform.core.configuration;

import com.abixen.platform.core.configuration.properties.RegisteredModuleServicesConfigurationProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

import static com.abixen.platform.common.util.PlatformProfiles.DOCKER;
import static com.abixen.platform.common.util.PlatformProfiles.DEV;


@Profile({DEV, DOCKER})
@Configuration
public class PlatformRabbitMQConfiguration {

    private final RegisteredModuleServicesConfigurationProperties registeredModuleServicesConfigurationProperties;

    @Autowired
    public PlatformRabbitMQConfiguration(RegisteredModuleServicesConfigurationProperties registeredModuleServicesConfigurationProperties) {
        this.registeredModuleServicesConfigurationProperties = registeredModuleServicesConfigurationProperties;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("abixen-platform-topic-exchange");
    }

    @Bean
    List<Queue> queues() {
        List<Queue> queues = new ArrayList<>();

        registeredModuleServicesConfigurationProperties.getRegisteredServices().forEach(registeredService -> {
            queues.add(new Queue(registeredService.getServiceId(), false));
        });

        return queues;
    }

    @Bean
    List<Binding> binding(TopicExchange exchange) {
        List<Binding> bindings = new ArrayList<>();

        queues().forEach(queue -> {
            bindings.add(BindingBuilder.bind(queue).to(exchange).with(queue.getName()));
        });

        return bindings;
    }

}

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

package com.abixen.platform.businessintelligence.configuration;

import com.abixen.platform.core.rabbitmq.AbstractRabbitMQConfiguration;
import com.abixen.platform.businessintelligence.rabbitmq.MessageReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.abixen.platform.core.util.PlatformProfiles.CLOUD;
import static com.abixen.platform.core.util.PlatformProfiles.DEV;


@Profile({DEV, CLOUD})
@Slf4j
@Configuration
public class PlatformModuleRabbitMQConfiguration extends AbstractRabbitMQConfiguration {

    private static final String QUEUE_NAME = "abixen-platform-modules";


    public PlatformModuleRabbitMQConfiguration() {
        super(QUEUE_NAME);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver messageReceiver) {
        return new MessageListenerAdapter(messageReceiver, "receiveMessage");
    }

}
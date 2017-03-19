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

package com.abixen.platform.common.rabbitmq;


import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;

public abstract class AbstractRabbitMQConfiguration {

    private final String queueName;

    private final int maxAttempts = 5;
    private final long initialInterval = 500;
    private final double multiplier = 2;
    private final long maxInterval = 8000;

    public AbstractRabbitMQConfiguration(String queueName) {
        this.queueName = queueName;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);

        container.setAdviceChain(new Advice[]{
                org.springframework.amqp.rabbit.config.RetryInterceptorBuilder
                        .stateless()
                        .maxAttempts(maxAttempts)
                        .backOffOptions(initialInterval, multiplier, maxInterval)
                        .build()
        });
        return container;
    }
}
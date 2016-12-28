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

package com.abixen.platform.core.service.impl;

import com.abixen.platform.core.rabbitmq.message.RabbitMQMessage;
import com.abixen.platform.core.service.RabbitMQOperations;
import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.stereotype.Service;


@Service
public class RabbitMQOperationsMockupImpl implements RabbitMQOperations {

    private final Logger log = Logger.getLogger(ModuleServiceImpl.class.getName());

    @Override
    public void convertAndSend(String routingKey, RabbitMQMessage rabbitMQMessage) throws AmqpException {
        log.info("This is mockup. The message has been sent.");
    }

}
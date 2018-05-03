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

package com.abixen.platform.core.interfaces.amqp;

import com.abixen.platform.common.interfaces.amqp.command.DeleteModuleCommand;
import com.abixen.platform.core.application.service.DeleteModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@EnableBinding(DeleteModuleSource.class)
public class AmqpDeleteModuleService implements DeleteModuleService {

    private final DeleteModuleSource deleteModuleSource;

    @Autowired
    public AmqpDeleteModuleService(DeleteModuleSource deleteModuleSource) {
        this.deleteModuleSource = deleteModuleSource;
    }

    @Override
    public void delete(final String routingKey, final DeleteModuleCommand deleteModuleCommand) {
        log.info("will send {}", deleteModuleCommand);
        try {
            final boolean sent = deleteModuleSource.output().send(
                    MessageBuilder.withPayload(deleteModuleCommand)
                            .setHeader(SimpMessageHeaderAccessor.DESTINATION_HEADER, routingKey)
                            .build());

            log.info("sent {} {}", sent, deleteModuleCommand);
        } catch (final Exception e) {
            log.error("Couldn't send command ", e);
        }
    }

}
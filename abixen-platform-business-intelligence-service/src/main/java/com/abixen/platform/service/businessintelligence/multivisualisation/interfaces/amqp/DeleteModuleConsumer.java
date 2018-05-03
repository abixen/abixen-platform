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

package com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.amqp;


import com.abixen.platform.common.infrastructure.exception.PlatformRuntimeException;
import com.abixen.platform.common.interfaces.amqp.command.DeleteModuleCommand;
import com.abixen.platform.service.businessintelligence.multivisualisation.application.service.ChartConfigurationManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(DeleteModuleProcessor.class)
public class DeleteModuleConsumer {

    private final ChartConfigurationManagementService chartConfigurationManagementService;

    @Autowired
    public DeleteModuleConsumer(ChartConfigurationManagementService chartConfigurationManagementService) {
        this.chartConfigurationManagementService = chartConfigurationManagementService;
    }


    @StreamListener(DeleteModuleProcessor.INPUT)
    public void consume(final Message<DeleteModuleCommand> message) {
        final DeleteModuleCommand deleteModuleCommand = message.getPayload();
        log.info("Received message {}", deleteModuleCommand);

        try {
            switch (deleteModuleCommand.getModuleTypeName()) {
                case "multi-visualisation":
                    chartConfigurationManagementService.deleteChartConfiguration(deleteModuleCommand.getModuleId());
                    break;
                default:
                    throw new PlatformRuntimeException("Wrong module type name: " + deleteModuleCommand.getModuleTypeName());
            }
        } catch (Exception e) {
            log.error("Can not delete module: {}", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }

        log.info("Module id: {}, type: {} removed successfully.", deleteModuleCommand.getModuleId(), deleteModuleCommand.getModuleTypeName());
    }

}
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

package com.abixen.platform.service.businessintelligence.rabbitmq;

import com.abixen.platform.common.rabbitmq.message.RabbitMQRemoveModuleMessage;
import com.abixen.platform.service.businessintelligence.multivisualisation.service.ChartConfigurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class MessageReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    private final ChartConfigurationService chartConfigurationService;

    public MessageReceiver(ChartConfigurationService chartConfigurationService) {
        this.chartConfigurationService = chartConfigurationService;
    }

    public void receiveMessage(RabbitMQRemoveModuleMessage message) {
        log.debug("receiveRabbitMQRemoveModuleMessage: moduleId={}, moduleTypeName={}", message.getModuleId(), message.getModuleTypeName());

        switch (message.getModuleTypeName()) {
            case "multi-visualisation":
                chartConfigurationService.removeChartConfiguration(message.getModuleId());
                break;
            default:
                throw new RuntimeException("Wrong moduleTypeName: " + message.getModuleTypeName());

        }

        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
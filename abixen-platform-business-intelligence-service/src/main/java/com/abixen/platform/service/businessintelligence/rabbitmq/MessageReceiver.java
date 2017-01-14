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

import com.abixen.platform.core.rabbitmq.message.RabbitMQRemoveModuleMessage;
import com.abixen.platform.service.businessintelligence.multivisualization.service.ChartConfigurationService;
import com.abixen.platform.service.businessintelligence.kpichart.service.KpiChartConfigurationService;
import com.abixen.platform.service.businessintelligence.magicnumber.service.MagicNumberModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class MessageReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    private final ChartConfigurationService chartConfigurationService;

    private final KpiChartConfigurationService kpiChartConfigurationService;

    private final MagicNumberModuleService magicNumberModuleService;

    public MessageReceiver(ChartConfigurationService chartConfigurationService,
                           KpiChartConfigurationService kpiChartConfigurationService,
                           MagicNumberModuleService magicNumberModuleService) {
        this.chartConfigurationService = chartConfigurationService;
        this.kpiChartConfigurationService = kpiChartConfigurationService;
        this.magicNumberModuleService = magicNumberModuleService;
    }

    public void receiveMessage(RabbitMQRemoveModuleMessage message) {
        log.debug("receiveRabbitMQRemoveModuleMessage: moduleId={}, moduleTypeName={}", message.getModuleId(), message.getModuleTypeName());

        switch (message.getModuleTypeName()) {
            case "multi-visualization":
                chartConfigurationService.removeChartConfiguration(message.getModuleId());
                break;
            case "magic-number":
                magicNumberModuleService.removeMagicNumberModule(message.getModuleId());
                break;
            case "kpi-chart":
                kpiChartConfigurationService.removeKpiChartConfiguration(message.getModuleId());
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
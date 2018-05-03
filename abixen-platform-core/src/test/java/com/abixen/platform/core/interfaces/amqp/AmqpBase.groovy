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

package com.abixen.platform.core.interfaces.amqp

import com.abixen.platform.common.interfaces.amqp.command.DeleteModuleCommand
import com.abixen.platform.core.AbstractPlatformIT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier

import java.util.concurrent.TimeUnit

abstract class AmqpBase extends AbstractPlatformIT {

    @Autowired
    private MessageVerifier messaging

    @Autowired
    private AmqpDeleteModuleService amqpDeleteModuleService

    void setup() {
        this.messaging.receive(DeleteModuleSource.OUTPUT, 100, TimeUnit.MILLISECONDS);
    }

    void initializePositiveSendDeleteModuleCommand() {
        final DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(1L, 'someModuleTypeName')
        this.amqpDeleteModuleService.delete('routingKey', deleteModuleCommand)
    }

}
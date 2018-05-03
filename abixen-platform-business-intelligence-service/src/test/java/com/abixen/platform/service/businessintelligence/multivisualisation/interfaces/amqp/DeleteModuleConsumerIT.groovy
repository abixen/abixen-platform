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

package com.abixen.platform.service.businessintelligence.multivisualisation.interfaces.amqp

import com.abixen.platform.service.businessintelligence.AbstractBusinessIntelligenceServiceIT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.contract.stubrunner.StubTrigger
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier
import org.springframework.messaging.MessagingException

@AutoConfigureStubRunner
class DeleteModuleConsumerIT extends AbstractBusinessIntelligenceServiceIT {

    @Autowired
    private StubTrigger stubTrigger;

    @Autowired
    private MessageVerifier messageVerifier;


    void 'should throw an exception when consume a message with wrong module type name'() {
        when: 'message with wrong module type name is received'
        stubTrigger.trigger('positive_sent_delete_module_command')

        then: 'thrown MessagingException'
        final MessagingException exception = thrown(MessagingException)
        exception.getCause().getCause().getMessage() == 'Wrong module type name: someModuleTypeName'
    }

}
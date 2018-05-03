package contracts.amqp

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    label 'positive_sent_delete_module_command'
    input {
        triggeredBy('initializePositiveSendDeleteModuleCommand()')
    }
    outputMessage {
        sentTo 'module.core.dx'
        body([
                moduleId      : 1L,
                moduleTypeName: 'someModuleTypeName'
        ])
        headers {
            header("contentType", applicationJsonUtf8())
        }
    }
}
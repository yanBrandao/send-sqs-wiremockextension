package com.github.extension

import com.github.extension.adapter.SQSMessageNotifier
import com.github.extension.model.InputPayload
import com.github.extension.utils.jsonObject
import com.github.extension.utils.objectToJson
import com.github.extension.utils.toProfile
import com.github.tomakehurst.wiremock.common.FileSource
import com.github.tomakehurst.wiremock.extension.Parameters
import com.github.tomakehurst.wiremock.extension.ResponseDefinitionTransformer
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.http.ResponseDefinition

class SendSQSTransform: ResponseDefinitionTransformer() {

    override fun getName() = EXTENSION_NAME

    override fun applyGlobally(): Boolean = false

    override fun transform(
        request: Request,
        responseDefinition: ResponseDefinition,
        fileSource: FileSource,
        parameters: Parameters?
    ): ResponseDefinition {
        println("Starting to transform request from wiremock [${request.body.decodeToString()}]")
        val payload = request.body.decodeToString().jsonObject(InputPayload::class.java)

        val sqsProfile = payload.toProfile()

        println("SQSProfile successfully converted: ${sqsProfile.objectToJson()}")

        val messenger = SQSMessageNotifier(sqsProfile)

        val messageSent = messenger.sendMessage(payload.data)

        println("Done to send async message with [id=${messageSent.messageId}] to SQS and finish transform message successfully!")
        return ResponseDefinition()
    }

    companion object {
        const val EXTENSION_NAME = "async-send-sqs"
    }
}

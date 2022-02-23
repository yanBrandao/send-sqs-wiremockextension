package com.github.extension.adapter

import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.services.sqs.model.SendMessageResult
import com.github.extension.adapter.SQSProfile.SQSEnvironment.*
import com.github.extension.utils.getLocalQueueURL

data class SQSMessageNotifier(
    val profile: SQSProfile
) {

    private val queueMessenger = AmazonSQSConfiguration(
            AmazonSQSProperties(
                endpoint = profile.sqsUrl
            )
        ).amazonSQSClient(profile)

    fun sendMessage(message: Any): SendMessageResult {
        val url = profile.queueName.getLocalQueueURL(profile.sqsUrl)
        println("Starting to send message to SQS $url")
        val request = SendMessageRequest()
            .withQueueUrl(url)
            .withMessageBody(message.toString())
        return queueMessenger.sendMessage(request).also {
            println("Done to send message to SQS $url")
        }
    }
}
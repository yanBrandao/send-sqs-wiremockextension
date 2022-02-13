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
                endpoint = "http://${profile.containerAddress}:4566"
            )
        ).amazonSQSClient(profile)

    fun sendMessage(message: Any): SendMessageResult {
        val url = when(profile.environment) {
            Cloud -> queueMessenger.getQueueUrl(profile.queueName).queueUrl
            Local, InDocker -> profile.queueName.getLocalQueueURL(profile.containerAddress)
        }

        println("Get full [URL=$url]")
        val request = SendMessageRequest()
            .withQueueUrl(url)
            .withMessageBody(message.toString())
        return queueMessenger.sendMessage(request)
    }
}
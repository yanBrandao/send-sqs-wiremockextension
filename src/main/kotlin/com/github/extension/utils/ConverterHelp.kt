package com.github.extension.utils

import com.github.extension.adapter.SQSProfile
import com.github.extension.adapter.SQSProfile.SQSEnvironment.*
import com.github.extension.model.InputPayload

fun InputPayload.toProfile(): SQSProfile =
    SQSProfile(
        queueName = this.metadata.queueName,
        sqsUrl = this.metadata.sqsUrl,
        environment = when(this.metadata.profile) {
            Local.name -> Local
            Cloud.name -> Cloud
            InDocker.name -> InDocker
            else -> throw RuntimeException("environment not configured")
        }
    )

fun String.getLocalQueueURL(sqsUrl: String) = "$sqsUrl/$this"

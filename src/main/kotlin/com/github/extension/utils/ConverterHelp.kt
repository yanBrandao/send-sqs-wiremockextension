package com.github.extension.utils

import com.github.extension.adapter.SQSProfile
import com.github.extension.adapter.SQSProfile.SQSEnvironment.*
import com.github.extension.model.InputPayload

fun InputPayload.toProfile(): SQSProfile =
    SQSProfile(
        queueName = this.metadata.queueName,
        containerName = this.metadata.containerName,
        environment = when(this.metadata.profile) {
            Local.name -> Local
            Cloud.name -> Cloud
            InDocker.name -> InDocker
            else -> throw RuntimeException("environment not configured")
        }
    )

fun String.getLocalQueueURL(environment: String) = "http://$environment:4566/000000000000/$this"

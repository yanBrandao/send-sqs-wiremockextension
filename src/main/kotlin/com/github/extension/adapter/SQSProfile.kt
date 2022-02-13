package com.github.extension.adapter

data class SQSProfile(
    val queueName: String,
    val containerName: String,
    val environment: SQSEnvironment
) {
   enum class SQSEnvironment {
       Local, Cloud, InDocker
   }
}
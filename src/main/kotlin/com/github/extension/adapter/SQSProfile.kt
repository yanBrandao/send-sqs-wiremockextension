package com.github.extension.adapter

data class SQSProfile(
    val queueName: String,
    val containerAddress: String,
    val environment: SQSEnvironment
) {
   enum class SQSEnvironment {
       Local, Cloud, InDocker
   }
}
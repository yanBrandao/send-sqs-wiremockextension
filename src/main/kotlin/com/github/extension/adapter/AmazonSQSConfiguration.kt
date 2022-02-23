package com.github.extension.adapter

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import com.github.extension.adapter.SQSProfile.SQSEnvironment.*
import com.github.extension.utils.objectToJson

class AmazonSQSConfiguration(
    private val amazonSQSProperties: AmazonSQSProperties
) {

    fun amazonSQSClient(profile: SQSProfile): AmazonSQSAsync{
        println("Starting to define SQS Client with [profile=${profile.environment}] and [url=${profile.sqsUrl}] and [queue=${profile.queueName}]",)
        return when (profile.environment) {
            Local -> amazonSQSAsyncLocal()
            Cloud -> amazonSQSAsync()
            InDocker -> amazonSQSAsyncLocal(profile.sqsUrl)
        }.also {
            println("Done to define SQS Client with [profile=${profile.environment}] and [url=${profile.sqsUrl}] and [queue=${profile.queueName}]")
        }
    }

    private fun amazonSQSAsyncLocal(endpoint: String): AmazonSQSAsync =
        AmazonSQSAsyncClientBuilder.standard()
            .withEndpointConfiguration(localEndpoint(endpoint))
            .withCredentials(AWSStaticCredentialsProvider(localCredentials()))
            .build()


    private fun amazonSQSAsyncLocal(): AmazonSQSAsync =
        AmazonSQSAsyncClientBuilder.standard()
            .withEndpointConfiguration(localEndpoint())
            .withCredentials(AWSStaticCredentialsProvider(localCredentials()))
            .build()

    private fun localEndpoint(endpoint: String): AwsClientBuilder.EndpointConfiguration =
        AwsClientBuilder.EndpointConfiguration(endpoint, amazonSQSProperties.region)

    private fun localEndpoint(): AwsClientBuilder.EndpointConfiguration =
        localEndpoint(amazonSQSProperties.endpoint)

    private fun localCredentials(): BasicAWSCredentials =
        BasicAWSCredentials(amazonSQSProperties.accessKey, amazonSQSProperties.secretKey)

    private fun amazonSQSAsync(): AmazonSQSAsync {
        return AmazonSQSAsyncClientBuilder.standard()
            .withRequestHandlers().build()
    }

}
package com.github.extension.adapter

data class AmazonSQSProperties(
    val endpoint: String = "http://localhost:4566",
    val accessKey: String = "aws",
    val secretKey: String = "aws",
    val region: String = "sa-east-1"
)
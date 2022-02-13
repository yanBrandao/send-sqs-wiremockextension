package com.github.extension.model

import com.fasterxml.jackson.annotation.JsonProperty

data class InputPayload(
    val metadata: MetaData,
    val data: Any = ""
) {
    data class MetaData(
        @JsonProperty("queue_name")
        val queueName: String,
        val profile: String,
        @JsonProperty("container_address")
        val containerAddress: String
    )
}
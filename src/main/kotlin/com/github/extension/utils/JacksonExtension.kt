package com.github.extension.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

    val jacksonObjectMapper: ObjectMapper =
        ObjectMapper().registerModule(JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .registerModule(KotlinModule())


fun <T> String.jsonObject(t: Class<T>): T =
    jacksonObjectMapper.readValue(this, t)

fun <T> T.objectToJson(): String =
    jacksonObjectMapper.writeValueAsString(this)
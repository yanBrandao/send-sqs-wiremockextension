package com.github.extension

import com.github.extension.model.InputPayload
import com.github.extension.utils.objectToJson
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.jayway.restassured.RestAssured.given
import org.junit.Rule
import org.junit.Test


class SendSQSTransformTest {

    @get:Rule
    var wireMockRule: WireMockRule = WireMockRule(wireMockConfig().port(8080).extensions(SendSQSTransform()))

    @Test
    fun `should send sqs message when receive wiremock api rest`() {
        wireMockRule.stubFor(
            post(urlMatching("/operation"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("content-type", "application/json")
                        .withBody("{\"var\":\"$(value)\"}")
                        .withTransformers("async-send-sqs")
                )
        )

        val payload = InputPayload(metadata = InputPayload.MetaData(
            queueName = "sample-queue",
            profile = "Local",
            sqsUrl = "http://localhost:4566/000000000000")).objectToJson()

        given()
            .contentType("application/json")
            .body(payload)
            .post("/operation")
            .then()
            .statusCode(200)
    }
}
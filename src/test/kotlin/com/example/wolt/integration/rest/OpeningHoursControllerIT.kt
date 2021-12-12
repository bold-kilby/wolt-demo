package com.example.wolt.integration.rest

import com.example.wolt.integration.rest.to.OpeningHoursRequestTO
import com.example.wolt.testbed.OpeningHoursRequestTOFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus


@SpringBootTest(webEnvironment = RANDOM_PORT)
class OpeningHoursControllerIT {

    @LocalServerPort
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val s = "http://localhost:$port/opening-hours"

    @Test
    fun `A valid request to print opening hours should be answered with the correct string`() {
        val payload: OpeningHoursRequestTO = OpeningHoursRequestTOFactory.completeRequestTO
        val response = restTemplate.postForObject("http://localhost:$port/opening-hours",
            payload,
            String::class.java)
        Assertions.assertEquals(
            """
                Monday: Closed
                Tuesday: 10 AM - 6 PM, 7 PM - 8 PM
                Wednesday: Closed
                Thursday: 10:30 AM - 6 PM
                Friday: 10 AM - 1 AM
                Saturday: 10 AM - 1 AM
                Sunday: 12 PM - 9 PM
            """.trimIndent(),
            response
        )
    }

    @Test
    fun `An invalid request should result in a bad request response`() {
        val payload: OpeningHoursRequestTO = OpeningHoursRequestTOFactory.invalidRequestTO
        val response = restTemplate.postForEntity("http://localhost:$port/opening-hours",
            payload,
            String::class.java)

        Assertions.assertEquals(response.statusCode, HttpStatus.BAD_REQUEST)
    }
}

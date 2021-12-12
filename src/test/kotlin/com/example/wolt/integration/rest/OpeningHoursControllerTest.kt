package com.example.wolt.integration.rest

import com.example.wolt.business.model.OpeningHours
import com.example.wolt.integration.rest.to.OpeningHoursRequestTO
import com.example.wolt.service.OpeningHoursWriter
import com.example.wolt.service.mapper.OpeningHoursRequestTOMapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OpeningHoursControllerTest {

    private val mapper: OpeningHoursRequestTOMapper = mockk()

    private val writer: OpeningHoursWriter = mockk()

    private val openingHoursController = OpeningHoursController(mapper, writer)

    @Test
    fun `A request TO should be mapped, then written and the output returned`() {
        val openingHoursRequestTO: OpeningHoursRequestTO = mockk()
        val openingHours: OpeningHours = mockk()
        val writtenOpeningHours = "written opening hours"
        every { mapper.mapToOpeningHours(openingHoursRequestTO) } returns openingHours
        every { writer.write(openingHours) } returns writtenOpeningHours

        val responseBody = openingHoursController.printOpeningHours(openingHoursRequestTO).body

        Assertions.assertEquals(writtenOpeningHours, responseBody)
    }
}
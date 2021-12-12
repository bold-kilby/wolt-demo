package com.example.wolt.service.mapper

import com.example.wolt.integration.rest.to.OpeningHourTypeRequestTO.CLOSE
import com.example.wolt.integration.rest.to.OpeningHourTypeRequestTO.OPEN
import com.example.wolt.integration.rest.to.OpeningHoursRequestTO
import com.example.wolt.integration.rest.to.OpeningHoursTimePointRequestTO
import com.example.wolt.service.mapper.to.MappingDayOfWeek
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import com.example.wolt.testbed.OpeningHoursFactory
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OpeningHoursRequestTOMapperTest {

    private val validityChecker: OpeningHoursValidityChecker = mockk()

    private val mapper = OpeningHoursRequestTOMapper(validityChecker)

    @Test
    fun `When mapping a TO, the validity checker should be called`() {
        val requestTO: OpeningHoursRequestTO = mockk()
        val mappingTO: OpeningHoursMappingTO = mockk()

        every { validityChecker.checkValidity(any()) } just Runs
        every { requestTO.toOpeningHoursMappingTO() } returns mappingTO
        every { mappingTO.iterator() } returns MappingDayOfWeek.values().iterator()
        every { mappingTO.getOpeningHoursForDay(any()) } returns listOf()
        every { mappingTO.getOpeningHoursForDayFollowing(any()) } returns listOf()

        mapper.mapToOpeningHours(requestTO)

        verify { validityChecker.checkValidity(mappingTO) }
    }

    @Test
    fun `A valid TO should be mapped to the correct model`() {
        val requestTO = OpeningHoursRequestTO(
            listOf(),
            listOf(
                OpeningHoursTimePointRequestTO(OPEN, 36000),
                OpeningHoursTimePointRequestTO(CLOSE, 64800),
                OpeningHoursTimePointRequestTO(OPEN, 68400),
                OpeningHoursTimePointRequestTO(CLOSE, 72000)
            ),
            listOf(),
            listOf(OpeningHoursTimePointRequestTO(OPEN, 36000)),
            listOf(
                OpeningHoursTimePointRequestTO(CLOSE, 3600),
                OpeningHoursTimePointRequestTO(OPEN, 43200),
                OpeningHoursTimePointRequestTO(CLOSE, 75600)
            ),
            listOf(),
            listOf()
        )

        every { validityChecker.checkValidity(any()) } just Runs

        val openingHours = mapper.mapToOpeningHours(requestTO)

        val expectedOpeningHours = OpeningHoursFactory.standardOpeningHours
        Assertions.assertEquals(expectedOpeningHours, openingHours)
    }
}
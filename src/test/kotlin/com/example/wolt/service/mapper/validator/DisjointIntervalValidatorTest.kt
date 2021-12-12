package com.example.wolt.service.mapper.validator

import com.example.wolt.service.mapper.to.MappingDayOfWeek
import com.example.wolt.service.mapper.to.MappingDayOfWeek.MONDAY
import com.example.wolt.service.mapper.to.OpeningHourTypeMappingTO.CLOSE
import com.example.wolt.service.mapper.to.OpeningHourTypeMappingTO.OPEN
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import com.example.wolt.service.mapper.to.OpeningHoursTimePointMappingTO
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DisjointIntervalValidatorTest {

    private val validator = DisjointIntervalValidator()

    @Test
    fun `When validating a TO, if intervals on a day overlap, the validator should record an error`() {
        val openingHoursTO: OpeningHoursMappingTO = mockk()

        every { openingHoursTO.iterator() } returns MappingDayOfWeek.values().iterator()
        every { openingHoursTO.getOpeningHoursForDay(any()) } returns emptyList()
        every { openingHoursTO.getOpeningHoursForDay(MONDAY) } returns
                listOf(
                    OpeningHoursTimePointMappingTO(OPEN, 1),
                    OpeningHoursTimePointMappingTO(CLOSE, 0),
                )
        every { openingHoursTO.getOpeningHoursForDayFollowing(any()) } returns emptyList()

        val validationReport = validator.validate(openingHoursTO)

        Assertions.assertTrue(validationReport.containsErrors())
    }

    @Test
    fun `When validating a TO, if no intervals overlap, the validator should not record an error`() {
        val openingHoursTO: OpeningHoursMappingTO = mockk()

        every { openingHoursTO.iterator() } returns MappingDayOfWeek.values().iterator()
        every { openingHoursTO.getOpeningHoursForDay(any()) } returns emptyList()
        every { openingHoursTO.getOpeningHoursForDay(MONDAY) } returns
                listOf(
                    OpeningHoursTimePointMappingTO(OPEN, 0),
                    OpeningHoursTimePointMappingTO(CLOSE, 1),
                )
        every { openingHoursTO.getOpeningHoursForDayFollowing(any()) } returns emptyList()

        val validationReport = validator.validate(openingHoursTO)

        Assertions.assertFalse(validationReport.containsErrors())
    }
}
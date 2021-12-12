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

class TimesSizeValidatorTest {

    private val validator = TimesSizeValidator()

    @Test
    fun `When validating a TO, if a time is too large, the validator should record an error`() {
        val oneDayAndOneSecond: Long = 24 * 60 * 60
        val openingHoursTO: OpeningHoursMappingTO = mockk()

        every { openingHoursTO.iterator() } returns MappingDayOfWeek.values().iterator()
        every { openingHoursTO.getOpeningHoursForDay(any()) } returns emptyList()
        every { openingHoursTO.getOpeningHoursForDay(MONDAY) } returns
                listOf(OpeningHoursTimePointMappingTO(OPEN, oneDayAndOneSecond))

        val validationReport = validator.validate(openingHoursTO)

        Assertions.assertTrue(validationReport.containsErrors())
    }

    @Test
    fun `When validating a TO, if a time is too small, the validator should record an error`() {
        val invalidNegativeTime: Long = -1
        val openingHoursTO: OpeningHoursMappingTO = mockk()

        every { openingHoursTO.iterator() } returns MappingDayOfWeek.values().iterator()
        every { openingHoursTO.getOpeningHoursForDay(any()) } returns emptyList()
        every { openingHoursTO.getOpeningHoursForDay(MONDAY) } returns
                listOf(OpeningHoursTimePointMappingTO(OPEN, invalidNegativeTime))

        val validationReport = validator.validate(openingHoursTO)

        Assertions.assertTrue(validationReport.containsErrors())
    }

    @Test
    fun `When validating a TO, if all times have correct sizes, the validator should not record an error`() {
        val oneDay: Long = 24 * 60 * 60 - 1
        val openingHoursTO: OpeningHoursMappingTO = mockk()

        every { openingHoursTO.iterator() } returns MappingDayOfWeek.values().iterator()
        every { openingHoursTO.getOpeningHoursForDay(any()) } returns emptyList()
        every { openingHoursTO.getOpeningHoursForDay(MONDAY) } returns
                listOf(OpeningHoursTimePointMappingTO(OPEN, 0), OpeningHoursTimePointMappingTO(CLOSE, oneDay))

        val validationReport = validator.validate(openingHoursTO)

        Assertions.assertFalse(validationReport.containsErrors())
    }
}
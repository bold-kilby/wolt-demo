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

class AlternatingOpeningHourTypeValidatorTest {

    private val validator = AlternatingOpeningHourTypeValidator()

    @Test
    fun `When validating a TO, when types on the same day don't alternate, an error should be recorded`() {
        val openingHoursTO: OpeningHoursMappingTO = mockk()

        every { openingHoursTO.iterator() } returns MappingDayOfWeek.values().iterator()
        every { openingHoursTO.getOpeningHoursForDay(any()) } returns emptyList()
        every { openingHoursTO.getOpeningHoursForDay(MONDAY) } returns
                listOf(OpeningHoursTimePointMappingTO(CLOSE, 0), OpeningHoursTimePointMappingTO(CLOSE, 1))
        every { openingHoursTO.getOpeningHoursForDayFollowing(any()) } returns emptyList()

        val validationReport = validator.validate(openingHoursTO)

        Assertions.assertTrue(validationReport.containsErrors())
    }

    @Test
    fun `When validating a TO, when types on following days don't alternate, an error should be recorded`() {
        val openingHoursTO: OpeningHoursMappingTO = mockk()

        every { openingHoursTO.iterator() } returns MappingDayOfWeek.values().iterator()
        every { openingHoursTO.getOpeningHoursForDay(any()) } returns emptyList()
        every { openingHoursTO.getOpeningHoursForDay(MONDAY) } returns
                listOf(OpeningHoursTimePointMappingTO(OPEN, 0))
        every { openingHoursTO.getOpeningHoursForDayFollowing(any()) } returns emptyList()
        every { openingHoursTO.getOpeningHoursForDayFollowing(MONDAY) } returns
                listOf(OpeningHoursTimePointMappingTO(OPEN, 0))

        val validationReport = validator.validate(openingHoursTO)

        Assertions.assertTrue(validationReport.containsErrors())
    }

    @Test
    fun `When validating a TO, when types match on all days, no error should be recorded`() {
        val openingHoursTO: OpeningHoursMappingTO = mockk()

        every { openingHoursTO.iterator() } returns MappingDayOfWeek.values().iterator()
        every { openingHoursTO.getOpeningHoursForDay(any()) } returns emptyList()
        every { openingHoursTO.getOpeningHoursForDay(MONDAY) } returns
                listOf(
                    OpeningHoursTimePointMappingTO(OPEN, 0),
                    OpeningHoursTimePointMappingTO(CLOSE, 1),
                    OpeningHoursTimePointMappingTO(OPEN, 2)
                )
        every { openingHoursTO.getOpeningHoursForDayFollowing(any()) } returns emptyList()
        every { openingHoursTO.getOpeningHoursForDayFollowing(MONDAY) } returns
                listOf(OpeningHoursTimePointMappingTO(CLOSE, 0))

        val validationReport = validator.validate(openingHoursTO)

        Assertions.assertFalse(validationReport.containsErrors())
    }
}
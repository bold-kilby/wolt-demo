package com.example.wolt.service.mapper.validator

import com.example.wolt.service.mapper.to.MappingDayOfWeek.FRIDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.MONDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.SATURDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.SUNDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.THURSDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.TUESDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.WEDNESDAY
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AllWeekdaysValidatorTest {

    private val validator = AllWeekdaysValidator()

    @Test
    fun `When validating a TO, if a weekday is missing, the validator should record an error`() {
        val openingHoursTO: OpeningHoursMappingTO = mockk()
        every { openingHoursTO.allDays } returns setOf(MONDAY, TUESDAY, WEDNESDAY)

        val validationReport = validator.validate(openingHoursTO)

        Assertions.assertTrue(validationReport.containsErrors())
    }

    @Test
    fun `When validating a TO, if no weekday is missing, the validator should not record an error`() {
        val openingHoursTO: OpeningHoursMappingTO = mockk()
        every { openingHoursTO.allDays } returns setOf(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)

        val validationReport = validator.validate(openingHoursTO)

        Assertions.assertFalse(validationReport.containsErrors())
    }
}
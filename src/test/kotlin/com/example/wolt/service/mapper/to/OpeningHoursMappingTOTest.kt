package com.example.wolt.service.mapper.to

import com.example.wolt.service.mapper.to.MappingDayOfWeek.MONDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.SUNDAY
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OpeningHoursMappingTOTest {

    @Test
    fun `All opening hours for a specified day should be returned correctly`() {
        val timePoint: OpeningHoursTimePointMappingTO = mockk()
        val openingHoursTO = OpeningHoursMappingTO(
            mapOf(MONDAY to listOf(timePoint))
        )

        Assertions.assertEquals(listOf(timePoint), openingHoursTO.getOpeningHoursForDay(MONDAY))
    }

    @Test
    fun `All opening hours for the nex day should be returned correctly`() {
        val timePoint: OpeningHoursTimePointMappingTO = mockk()
        val openingHoursTO = OpeningHoursMappingTO(
            mapOf(MONDAY to listOf(timePoint))
        )

        Assertions.assertEquals(listOf(timePoint), openingHoursTO.getOpeningHoursForDayFollowing(SUNDAY))
    }
}
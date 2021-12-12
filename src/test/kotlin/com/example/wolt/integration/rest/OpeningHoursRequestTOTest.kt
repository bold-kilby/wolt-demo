package com.example.wolt.integration.rest

import com.example.wolt.service.mapper.to.MappingDayOfWeek.FRIDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.MONDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.SATURDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.SUNDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.THURSDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.TUESDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.WEDNESDAY
import com.example.wolt.service.mapper.to.OpeningHourTypeMappingTO.CLOSE
import com.example.wolt.service.mapper.to.OpeningHourTypeMappingTO.OPEN
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import com.example.wolt.service.mapper.to.OpeningHoursTimePointMappingTO
import com.example.wolt.testbed.OpeningHoursRequestTOFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OpeningHoursRequestTOTest {

    @Test
    fun `A request TO should map itself to a correct mapping TO`() {
        val requestTO = OpeningHoursRequestTOFactory.shortRequestTO
        val mappingTO = requestTO.toOpeningHoursMappingTO()

        val expectedMappingTO = OpeningHoursMappingTO(
            mapOf(
                MONDAY to listOf(),
                TUESDAY to listOf(
                    OpeningHoursTimePointMappingTO(OPEN, 36000),
                    OpeningHoursTimePointMappingTO(CLOSE, 64800)
                ),
                WEDNESDAY to listOf(),
                THURSDAY to listOf(),
                FRIDAY to listOf(OpeningHoursTimePointMappingTO(OPEN, 36000)),
                SATURDAY to listOf(OpeningHoursTimePointMappingTO(CLOSE, 3600)),
                SUNDAY to listOf()
            )
        )
        Assertions.assertEquals(expectedMappingTO, mappingTO)
    }
}
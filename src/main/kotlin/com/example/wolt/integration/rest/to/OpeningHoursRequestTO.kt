package com.example.wolt.integration.rest.to

import com.example.wolt.service.mapper.to.MappingDayOfWeek.FRIDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.MONDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.SATURDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.SUNDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.THURSDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.TUESDAY
import com.example.wolt.service.mapper.to.MappingDayOfWeek.WEDNESDAY
import com.example.wolt.service.mapper.to.OpeningHourTypeMappingTO
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import com.example.wolt.service.mapper.to.OpeningHoursTimePointMappingTO
import com.fasterxml.jackson.annotation.JsonProperty

data class OpeningHoursRequestTO(
    val monday: List<OpeningHoursTimePointRequestTO>,
    val tuesday: List<OpeningHoursTimePointRequestTO>,
    val wednesday: List<OpeningHoursTimePointRequestTO>,
    val thursday: List<OpeningHoursTimePointRequestTO>,
    val friday: List<OpeningHoursTimePointRequestTO>,
    val saturday: List<OpeningHoursTimePointRequestTO>,
    val sunday: List<OpeningHoursTimePointRequestTO>
) {
    fun toOpeningHoursMappingTO(): OpeningHoursMappingTO =
        OpeningHoursMappingTO(
            mapOf(
                MONDAY to monday.toOpeningHourTimePointMappingTOs(),
                TUESDAY to tuesday.toOpeningHourTimePointMappingTOs(),
                WEDNESDAY to wednesday.toOpeningHourTimePointMappingTOs(),
                THURSDAY to thursday.toOpeningHourTimePointMappingTOs(),
                FRIDAY to friday.toOpeningHourTimePointMappingTOs(),
                SATURDAY to saturday.toOpeningHourTimePointMappingTOs(),
                SUNDAY to sunday.toOpeningHourTimePointMappingTOs()
            )
        )

    private fun List<OpeningHoursTimePointRequestTO>.toOpeningHourTimePointMappingTOs(): List<OpeningHoursTimePointMappingTO> =
        map { it.toOpeningHoursTimePointMappingTO() }
}

data class OpeningHoursTimePointRequestTO(
    val type: OpeningHourTypeRequestTO,
    val value: Long
) {
    fun toOpeningHoursTimePointMappingTO(): OpeningHoursTimePointMappingTO =
        OpeningHoursTimePointMappingTO(type.toOpeningHourTypeMappingTO(), value)
}

enum class OpeningHourTypeRequestTO {
    @JsonProperty("open")
    OPEN,

    @JsonProperty("close")
    CLOSE;

    fun toOpeningHourTypeMappingTO(): OpeningHourTypeMappingTO =
        when (this) {
            OPEN -> OpeningHourTypeMappingTO.OPEN
            CLOSE -> OpeningHourTypeMappingTO.CLOSE
        }
}

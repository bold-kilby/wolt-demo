package com.example.wolt.service.mapper

import com.example.wolt.business.model.OpeningHour
import com.example.wolt.business.model.OpeningHours
import com.example.wolt.business.model.OpeningInterval
import com.example.wolt.integration.rest.to.OpeningHoursRequestTO
import com.example.wolt.service.mapper.to.OpeningHourTypeMappingTO.CLOSE
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import com.example.wolt.service.mapper.to.OpeningHoursTimePointMappingTO
import org.springframework.stereotype.Service

@Service
class OpeningHoursRequestTOMapper(private val openingHoursValidityChecker: OpeningHoursValidityChecker) {
    fun mapToOpeningHours(openingHoursRequestTO: OpeningHoursRequestTO): OpeningHours {
        val openingHoursTO = openingHoursRequestTO.toOpeningHoursMappingTO()
        openingHoursValidityChecker.checkValidity(openingHoursTO)
        return openingHoursTO.reorganize()
            .extractOpeningHours()
    }

    private fun OpeningHoursMappingTO.reorganize(): OpeningHoursMappingTO =
        OpeningHoursMappingTO(
            associateWith { day ->
                putTimePointsOnOpeningDay(
                    getOpeningHoursForDay(day),
                    getOpeningHoursForDayFollowing(day)
                )
            }
        )

    private fun putTimePointsOnOpeningDay(
        day: List<OpeningHoursTimePointMappingTO>,
        followingDay: List<OpeningHoursTimePointMappingTO>
    ): List<OpeningHoursTimePointMappingTO> {
        var newDay = day
        day.doIfFirstSlotIsClose { newDay = day.subList(1, day.size) }
        followingDay.doIfFirstSlotIsClose { firstSlot -> newDay = newDay.plus(firstSlot) }
        return newDay
    }

    fun List<OpeningHoursTimePointMappingTO>.doIfFirstSlotIsClose(action: (OpeningHoursTimePointMappingTO) -> Unit) {
        getOrNull(0)?.let { firstSlot ->
            if (firstSlot.type == CLOSE) {
                action(firstSlot)
            }
        }
    }

    private fun OpeningHoursMappingTO.extractOpeningHours(): OpeningHours =
        OpeningHours(this.map { OpeningHour(it.dayName, this.getOpeningHoursForDay(it).toOpeningIntervals()) })

    private fun List<OpeningHoursTimePointMappingTO>.toOpeningIntervals(): List<OpeningInterval> =
        chunked(2).map { toOpeningHour(it[0], it[1]) }

    private fun toOpeningHour(
        openOpeningHoursTimePointTO: OpeningHoursTimePointMappingTO,
        closeOpeningHoursTimePointTO: OpeningHoursTimePointMappingTO
    ): OpeningInterval =
        OpeningInterval(openOpeningHoursTimePointTO.value, closeOpeningHoursTimePointTO.value)

}


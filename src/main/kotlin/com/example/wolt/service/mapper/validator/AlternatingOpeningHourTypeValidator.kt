package com.example.wolt.service.mapper.validator

import com.example.wolt.business.model.ValidationReport
import com.example.wolt.service.mapper.to.OpeningHourTypeMappingTO.CLOSE
import com.example.wolt.service.mapper.to.OpeningHourTypeMappingTO.OPEN
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import com.example.wolt.service.mapper.to.OpeningHoursTimePointMappingTO
import org.springframework.stereotype.Service

@Service
class AlternatingOpeningHourTypeValidator : OpeningHoursValidator {
    override fun validate(
        openingHoursTO: OpeningHoursMappingTO
    ): ValidationReport {
        val errors = mutableListOf<String>()
        openingHoursTO.forEach { day ->
            checkTypesAlternate(
                openingHoursTO.getOpeningHoursForDay(day),
                openingHoursTO.getOpeningHoursForDayFollowing(day),
                errors
            )
        }
        return ValidationReport(errors)
    }

    private fun checkTypesAlternate(
        day: List<OpeningHoursTimePointMappingTO>,
        nextDay: List<OpeningHoursTimePointMappingTO>,
        errors: MutableList<String>
    ) {
        if (day.isEmpty()) {
            return
        }
        checkTypesAlternateOnSameDay(day, errors)
        checkTypesValidAcrossDays(day, nextDay, errors)
    }

    private fun checkTypesAlternateOnSameDay(
        day: List<OpeningHoursTimePointMappingTO>,
        errors: MutableList<String>
    ) {
        day.zipWithNext().forEach { (point1, point2) ->
            if (point1.type == point2.type) {
                errors.add("Opening hours with times ${point1.value}, ${point2.value} have same type ${point1.type}")
            }
        }
    }

    private fun checkTypesValidAcrossDays(
        day: List<OpeningHoursTimePointMappingTO>,
        nextDay: List<OpeningHoursTimePointMappingTO>,
        errors: MutableList<String>
    ) {
        if (day.isEmpty()) {
            return
        }
        val lastPointOfDay = day[day.lastIndex]
        if (lastPointOfDay.type == OPEN) {
            if (followingTimePointDoesNotMatch(nextDay)) {
                errors.add(
                    "Opening hour type of slot on next day following time ${lastPointOfDay.value} does not match" +
                            " type ${lastPointOfDay.type}"
                )
            }
        }
    }

    private fun followingTimePointDoesNotMatch(nextDay: List<OpeningHoursTimePointMappingTO>) =
        nextDay.isEmpty() || nextDay[0].type != CLOSE
}
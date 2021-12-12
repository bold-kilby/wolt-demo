package com.example.wolt.service.mapper.validator

import com.example.wolt.business.model.ValidationReport
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import com.example.wolt.service.mapper.to.OpeningHoursTimePointMappingTO
import org.springframework.stereotype.Service

@Service
class DisjointIntervalValidator : OpeningHoursValidator {
    override fun validate(openingHoursTO: OpeningHoursMappingTO): ValidationReport {
        val errors = checkForValidIntervals(openingHoursTO)
        return ValidationReport(errors)
    }

    private fun checkForValidIntervals(openingHoursTO: OpeningHoursMappingTO): List<String> {
        val errors = mutableListOf<String>()
        openingHoursTO.forEach { day ->
            checkIntervalsForDay(
                openingHoursTO.getOpeningHoursForDay(day),
                errors
            )
        }
        return errors.toList()
    }

    private fun checkIntervalsForDay(
        timePoints: List<OpeningHoursTimePointMappingTO>,
        errors: MutableList<String>
    ) {
        timePoints.zipWithNext().forEach { (startPoint, endPoint) ->
            checkEndpointsAreOrdered(startPoint, endPoint, errors)
        }
    }

    private fun checkEndpointsAreOrdered(
        startPoint: OpeningHoursTimePointMappingTO,
        endPoint: OpeningHoursTimePointMappingTO,
        errors: MutableList<String>
    ) {
        if (startPoint.value >= endPoint.value) {
            errors.add(
                "Opening hours with times ${startPoint.value}, ${endPoint.value} " +
                        "do not form a valid disjoint interval"
            )
        }
    }

}
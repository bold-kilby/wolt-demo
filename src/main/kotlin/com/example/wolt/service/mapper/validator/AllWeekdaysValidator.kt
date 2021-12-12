package com.example.wolt.service.mapper.validator

import com.example.wolt.business.model.ValidationReport
import com.example.wolt.service.mapper.to.MappingDayOfWeek
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import org.springframework.stereotype.Service

@Service
class AllWeekdaysValidator : OpeningHoursValidator{
    override fun validate(openingHoursTO: OpeningHoursMappingTO): ValidationReport {
        val errors = mutableListOf<String>()
        val missingWeekdays = openingHoursTO.missingWeekdays()
        if (missingWeekdays.isNotEmpty()) {
            errors.add("Some weekdays are missing: ${missingWeekdays.joinToString()}")
        }
        return ValidationReport(errors)
    }

    private fun OpeningHoursMappingTO.missingWeekdays(): Set<MappingDayOfWeek> =
        MappingDayOfWeek.values().toSet().minus(allDays)
}
package com.example.wolt.service.mapper.validator

import com.example.wolt.business.model.ValidationReport
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import com.example.wolt.service.mapper.to.OpeningHoursTimePointMappingTO
import org.springframework.stereotype.Service

@Service
class TimesSizeValidator : OpeningHoursValidator{
    override fun validate(
        openingHoursTO: OpeningHoursMappingTO
    ): ValidationReport {
        val errors = mutableListOf<String>()
        openingHoursTO.forEach { day ->
            checkTimesHaveCorrectSize(
                openingHoursTO.getOpeningHoursForDay(day),
                errors
            )
        }
        return ValidationReport(errors)
    }

    private fun checkTimesHaveCorrectSize(
        timesForDay: List<OpeningHoursTimePointMappingTO>,
        errors: MutableList<String>
    ) {
        timesForDay.forEach {
            checkTimeIsNotTooLarge(it, errors)
            checkTimeIsNotTooSmall(it, errors)
        }
    }

    private fun checkTimeIsNotTooLarge(
        timePointTO: OpeningHoursTimePointMappingTO,
        errors: MutableList<String>
    ) {
        if (timePointTO.value > ONE_DAY_IN_SECONDS) {
            errors.add("Opening hour value ${timePointTO.value} is larger than 24 hours")
        }
    }

    private fun checkTimeIsNotTooSmall(
        timePointTO: OpeningHoursTimePointMappingTO,
        errors: MutableList<String>
    ) {
        if (timePointTO.value < START_OF_DAY_IN_SECONDS) {
            errors.add("Opening hour value ${timePointTO.value} is smaller than $START_OF_DAY_IN_SECONDS")
        }
    }

    companion object {
        private const val ONE_DAY_IN_SECONDS = 24 * 60 * 60 - 1
        private const val START_OF_DAY_IN_SECONDS = 0
    }
}
package com.example.wolt.service.mapper

import com.example.wolt.business.exception.InvalidOpeningHoursRequestTOException
import com.example.wolt.business.model.ValidationReport
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import com.example.wolt.service.mapper.validator.OpeningHoursValidator
import org.springframework.stereotype.Service

@Service
class OpeningHoursValidityChecker(private val openingHoursValidators: List<OpeningHoursValidator>) {
    fun checkValidity(openingHoursTO: OpeningHoursMappingTO) {
        var validationReport = ValidationReport()
        openingHoursValidators.forEach {
            validationReport = validationReport.joinWith(it.validate(openingHoursTO))
        }
        if (validationReport.containsErrors()) {
            throw InvalidOpeningHoursRequestTOException(
                "Encountered errors in request TO: \n ${validationReport.writeErrorsOnSeparateLines()}"
            )
        }
    }
}
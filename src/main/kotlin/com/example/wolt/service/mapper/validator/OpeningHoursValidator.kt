package com.example.wolt.service.mapper.validator

import com.example.wolt.business.model.ValidationReport
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO

interface OpeningHoursValidator {
    fun validate(openingHoursTO: OpeningHoursMappingTO): ValidationReport
}
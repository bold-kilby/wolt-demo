package com.example.wolt.business.model

import com.example.wolt.util.writeOnSeparateLines

data class ValidationReport(val errors: List<String> = listOf()) {
    fun containsErrors(): Boolean = errors.isNotEmpty()

    fun writeErrorsOnSeparateLines() = errors.writeOnSeparateLines()

    fun joinWith(validationReport: ValidationReport) = ValidationReport(errors.plus(validationReport.errors))
}

package com.example.wolt.business.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ValidationReportTest {

    @Test
    fun `When writing a report on separate lines, it should be written correctly`() {
        val report = ValidationReport(listOf("error 1", "error 2"))
        val writtenReport = report.writeErrorsOnSeparateLines()

        val expectedWrittenReport = """
            error 1
            error 2
        """.trimIndent()
        Assertions.assertEquals(expectedWrittenReport, writtenReport)
    }

    @Test
    fun `Errors should be reported as present if they are`() {
        val report = ValidationReport(listOf("error 1"))
        Assertions.assertTrue(report.containsErrors())
    }

    @Test
    fun `If there are no errors this should be reported correctly`() {
        val report = ValidationReport()
        Assertions.assertFalse(report.containsErrors())
    }

    @Test
    fun `Two validation reports should be joined correctly`() {
        val error1 = "error 1"
        val report1 = ValidationReport(listOf(error1))
        val error2 = "error 2"
        val report2 = ValidationReport(listOf(error2))

        val joinedReport = report1.joinWith(report2)
        val expectedReport = ValidationReport(listOf(error1, error2))

        Assertions.assertEquals(expectedReport, joinedReport)
    }
}
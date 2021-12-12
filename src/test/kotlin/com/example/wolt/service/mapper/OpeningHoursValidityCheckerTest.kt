package com.example.wolt.service.mapper

import com.example.wolt.business.exception.InvalidOpeningHoursRequestTOException
import com.example.wolt.business.model.ValidationReport
import com.example.wolt.service.mapper.to.OpeningHoursMappingTO
import com.example.wolt.service.mapper.validator.OpeningHoursValidator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class OpeningHoursValidityCheckerTest {

    private val validator1: OpeningHoursValidator = mockk()

    private val validator2: OpeningHoursValidator = mockk()

    private val validityChecker = OpeningHoursValidityChecker(listOf(validator1, validator2))

    @Test
    fun `When checking a TO, the validity checker should call all validators`() {
        val openingHoursTO: OpeningHoursMappingTO = mockk()
        every { validator1.validate(openingHoursTO) } returns ValidationReport()
        every { validator2.validate(openingHoursTO) } returns ValidationReport()

        validityChecker.checkValidity(openingHoursTO)

        verify { validator1.validate(openingHoursTO) }
        verify { validator2.validate(openingHoursTO) }
    }

    @Test
    fun `When checking a TO, if one validator reports an error, an exception should be thrown`() {
        val openingHoursTO: OpeningHoursMappingTO = mockk()
        val errors = listOf("error message")
        every { validator1.validate(openingHoursTO) } returns ValidationReport(errors)
        every { validator2.validate(openingHoursTO) } returns ValidationReport()

        assertFailsWith<InvalidOpeningHoursRequestTOException> {
            validityChecker.checkValidity(openingHoursTO)
        }
    }
}
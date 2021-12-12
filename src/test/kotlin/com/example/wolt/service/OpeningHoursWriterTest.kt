package com.example.wolt.service

import com.example.wolt.business.model.OpeningHour
import com.example.wolt.business.model.OpeningHourDay.MONDAY
import com.example.wolt.business.model.OpeningHourDay.TUESDAY
import com.example.wolt.business.model.OpeningHours
import com.example.wolt.business.model.OpeningInterval
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OpeningHoursWriterTest {

    private val timeWriter: TimeWriter = mockk()

    private val openingHoursWriter = OpeningHoursWriter(timeWriter)

    @Test
    fun `The time writer should be used to format the opening hours`() {
        val open: Long = 0
        val close: Long = 1
        val openingHours = OpeningHours(
            listOf(
                OpeningHour(MONDAY, listOf(OpeningInterval(open, close)))
            )
        )
        every { timeWriter.write(any()) } returns ""

        openingHoursWriter.write(openingHours)

        verify { timeWriter.write(open) }
        verify { timeWriter.write(close) }
    }

    @Test
    fun `Opening hours should be written correctly`() {
        val open: Long = 0
        val close: Long = 3600
        val openingHours = OpeningHours(
            listOf(
                OpeningHour(MONDAY, listOf(OpeningInterval(open, close))),
                OpeningHour(TUESDAY, listOf())
            )
        )
        every { timeWriter.write(0) } returns "0:00 AM"
        every { timeWriter.write(3600) } returns "1:00 AM"

        val writtenOpeningHours = openingHoursWriter.write(openingHours)

        val expectedWrittenOpeningHours = """
            Monday: 0:00 AM - 1:00 AM
            Tuesday: Closed
        """.trimIndent()
        Assertions.assertEquals(expectedWrittenOpeningHours, writtenOpeningHours)
    }
}
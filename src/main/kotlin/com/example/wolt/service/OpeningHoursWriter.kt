package com.example.wolt.service

import com.example.wolt.business.model.OpeningHour
import com.example.wolt.business.model.OpeningHours
import com.example.wolt.business.model.OpeningInterval
import com.example.wolt.util.writeOnSeparateLines
import org.springframework.stereotype.Service

@Service
class OpeningHoursWriter(private val timeWriter: TimeWriter) {
    fun write(openingHours: OpeningHours): String =
        openingHours.map { write(it) }
            .writeOnSeparateLines()

    private fun write(openingHour: OpeningHour): String =
        "${openingHour.day.dayName}: ${write(openingHour.openingIntervals)}"

    private fun write(openingHourIntervals: List<OpeningInterval>): String =
        if (openingHourIntervals.isEmpty()) {
            CLOSED_EXPRESSION
        } else {
            openingHourIntervals.joinToString { writeOpeningInterval(it) }
        }

    private fun writeOpeningInterval(openingInterval: OpeningInterval): String =
        "${timeWriter.write(openingInterval.open)} - ${timeWriter.write(openingInterval.close)}"

    companion object {
        private const val CLOSED_EXPRESSION = "Closed"
    }
}

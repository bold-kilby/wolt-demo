package com.example.wolt.service

import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

@Service
class TimeWriter {

    fun write(seconds: Long): String {
        val secondsTrimmed = seconds.trimSeconds()
        val date = Date.from(Instant.ofEpochSecond(secondsTrimmed))
        return if (secondsTrimmed.representFullHour()) {
            AM_PM_FORMATTER_WITHOUT_MINUTES.format(date)
        } else {
            AM_PM_FORMATTER_WITH_MINUTES.format(date)
        }
    }

    private fun Long.trimSeconds(): Long = this - mod(SECONDS_IN_ONE_MINUTE)

    private fun Long.representFullHour(): Boolean = mod(SECONDS_IN_ONE_HOUR) == 0

    companion object {
        private val AM_PM_FORMATTER_WITH_MINUTES = SimpleDateFormat("h:mm a")
        private val AM_PM_FORMATTER_WITHOUT_MINUTES = SimpleDateFormat("h a")

        init {
            AM_PM_FORMATTER_WITH_MINUTES.timeZone = TimeZone.getTimeZone("GMT")
            AM_PM_FORMATTER_WITHOUT_MINUTES.timeZone = TimeZone.getTimeZone("GMT")
        }

        private const val SECONDS_IN_ONE_HOUR = 3600
        private const val SECONDS_IN_ONE_MINUTE = 60
    }
}
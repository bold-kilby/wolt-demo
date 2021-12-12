package com.example.wolt.testbed

import com.example.wolt.business.model.OpeningHour
import com.example.wolt.business.model.OpeningHours
import com.example.wolt.business.model.OpeningInterval

object OpeningHoursFactory {
    val standardOpeningHours: OpeningHours
        get() = OpeningHours(
            listOf(
                OpeningHour("Monday", listOf()),
                OpeningHour(
                    "Tuesday",
                    listOf(
                        OpeningInterval(36000, 64800),
                        OpeningInterval(68400, 72000)
                    )
                ),
                OpeningHour("Wednesday", listOf()),
                OpeningHour("Thursday", listOf(OpeningInterval(36000, 3600))),
                OpeningHour("Friday", listOf(OpeningInterval(43200, 75600))),
                OpeningHour("Saturday", listOf()),
                OpeningHour("Sunday", listOf())
            )
        )
}
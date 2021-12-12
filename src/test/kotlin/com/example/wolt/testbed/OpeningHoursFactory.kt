package com.example.wolt.testbed

import com.example.wolt.business.model.OpeningHour
import com.example.wolt.business.model.OpeningHourDay.FRIDAY
import com.example.wolt.business.model.OpeningHourDay.MONDAY
import com.example.wolt.business.model.OpeningHourDay.SATURDAY
import com.example.wolt.business.model.OpeningHourDay.SUNDAY
import com.example.wolt.business.model.OpeningHourDay.THURSDAY
import com.example.wolt.business.model.OpeningHourDay.TUESDAY
import com.example.wolt.business.model.OpeningHourDay.WEDNESDAY
import com.example.wolt.business.model.OpeningHours
import com.example.wolt.business.model.OpeningInterval

object OpeningHoursFactory {
    val standardOpeningHours: OpeningHours
        get() = OpeningHours(
            listOf(
                OpeningHour(MONDAY, listOf()),
                OpeningHour(
                    TUESDAY,
                    listOf(
                        OpeningInterval(36000, 64800),
                        OpeningInterval(68400, 72000)
                    )
                ),
                OpeningHour(WEDNESDAY, listOf()),
                OpeningHour(THURSDAY, listOf(OpeningInterval(36000, 3600))),
                OpeningHour(FRIDAY, listOf(OpeningInterval(43200, 75600))),
                OpeningHour(SATURDAY, listOf()),
                OpeningHour(SUNDAY, listOf())
            )
        )
}
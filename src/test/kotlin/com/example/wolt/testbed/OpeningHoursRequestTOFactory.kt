package com.example.wolt.testbed

import com.example.wolt.integration.rest.to.OpeningHourTypeRequestTO.CLOSE
import com.example.wolt.integration.rest.to.OpeningHourTypeRequestTO.OPEN
import com.example.wolt.integration.rest.to.OpeningHoursRequestTO
import com.example.wolt.integration.rest.to.OpeningHoursTimePointRequestTO

object OpeningHoursRequestTOFactory {
    val shortRequestTO: OpeningHoursRequestTO
        get() = OpeningHoursRequestTO(
            monday = listOf(),
            tuesday = listOf(
                OpeningHoursTimePointRequestTO(OPEN, 36000),
                OpeningHoursTimePointRequestTO(CLOSE, 64800),
            ),
            wednesday = listOf(),
            thursday = listOf(),
            friday = listOf(OpeningHoursTimePointRequestTO(OPEN, 36000)),
            saturday = listOf(OpeningHoursTimePointRequestTO(CLOSE, 3600)),
            sunday = listOf()
        )

    val completeRequestTO: OpeningHoursRequestTO
        get() = OpeningHoursRequestTO(
            monday = listOf(),
            tuesday = listOf(
                OpeningHoursTimePointRequestTO(OPEN, 36000),
                OpeningHoursTimePointRequestTO(CLOSE, 64800),
                OpeningHoursTimePointRequestTO(OPEN, 68400),
                OpeningHoursTimePointRequestTO(CLOSE, 72000)
            ),
            wednesday = listOf(),
            thursday = listOf(
                OpeningHoursTimePointRequestTO(OPEN, 37800),
                OpeningHoursTimePointRequestTO(CLOSE, 64800)
            ),
            friday = listOf(OpeningHoursTimePointRequestTO(OPEN, 36000)),
            saturday = listOf(
                OpeningHoursTimePointRequestTO(CLOSE, 3600),
                OpeningHoursTimePointRequestTO(OPEN, 36000)
            ),
            sunday = listOf(
                OpeningHoursTimePointRequestTO(CLOSE, 3600),
                OpeningHoursTimePointRequestTO(OPEN, 43200),
                OpeningHoursTimePointRequestTO(CLOSE, 75600)
            )
        )

    val invalidRequestTO: OpeningHoursRequestTO
        get() = OpeningHoursRequestTO(
            monday = listOf(),
            tuesday = listOf(
                OpeningHoursTimePointRequestTO(OPEN, 36000),
                OpeningHoursTimePointRequestTO(OPEN, 64800),
            ),
            wednesday = listOf(
                OpeningHoursTimePointRequestTO(OPEN, 36000),
                OpeningHoursTimePointRequestTO(CLOSE, 7200),
            ),
            thursday = listOf(),
            friday = listOf(OpeningHoursTimePointRequestTO(CLOSE, 36000)),
            saturday = listOf(OpeningHoursTimePointRequestTO(CLOSE, 3600)),
            sunday = listOf()
        )
}
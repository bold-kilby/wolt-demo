package com.example.wolt.business.model

data class OpeningHours(
    val openingHours: List<OpeningHour>
) : Iterable<OpeningHour> {
    override fun iterator() = openingHours.iterator()
}

data class OpeningHour(
    val day: OpeningHourDay,
    val openingIntervals: List<OpeningInterval>
)

enum class OpeningHourDay(val dayName: String) {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");
}

data class OpeningInterval(
    val open: Long,
    val close: Long
)

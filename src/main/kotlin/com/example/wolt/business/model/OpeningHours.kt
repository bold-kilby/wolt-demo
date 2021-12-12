package com.example.wolt.business.model

data class OpeningHours(
    val openingHours: List<OpeningHour>
) : Iterable<OpeningHour> {
    override fun iterator() = openingHours.iterator()
}

data class OpeningHour(
    val dayName: String,
    val openingIntervals: List<OpeningInterval>
)

data class OpeningInterval(
    val open: Long,
    val close: Long
)

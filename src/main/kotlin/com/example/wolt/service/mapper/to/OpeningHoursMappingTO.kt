package com.example.wolt.service.mapper.to

data class OpeningHoursMappingTO(
    private val openingHours: Map<MappingDayOfWeek, List<OpeningHoursTimePointMappingTO>>
) : Iterable<MappingDayOfWeek> {
    override fun iterator() = allDays.iterator()

    val allDays: Set<MappingDayOfWeek>
        get() = openingHours.keys

    fun getOpeningHoursForDay(day: MappingDayOfWeek): List<OpeningHoursTimePointMappingTO> =
        openingHours.getValue(day)

    fun getOpeningHoursForDayFollowing(day: MappingDayOfWeek): List<OpeningHoursTimePointMappingTO> =
        openingHours.getValue(day.followingDay())
}


enum class MappingDayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    fun followingDay(): MappingDayOfWeek = when (this) {
        MONDAY -> TUESDAY
        TUESDAY -> WEDNESDAY
        WEDNESDAY -> THURSDAY
        THURSDAY -> FRIDAY
        FRIDAY -> SATURDAY
        SATURDAY -> SUNDAY
        SUNDAY -> MONDAY
    }
}

data class OpeningHoursTimePointMappingTO(
    val type: OpeningHourTypeMappingTO,
    val value: Long
)

enum class OpeningHourTypeMappingTO {
    OPEN, CLOSE
}
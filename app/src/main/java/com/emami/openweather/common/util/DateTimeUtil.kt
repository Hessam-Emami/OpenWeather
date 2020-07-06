package com.emami.openweather.common.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeUtil {
    fun formatLocalDateTimeToHourAmPm(dateTime: LocalDateTime): String =
        DateTimeFormatter.ofPattern("ha").format(
            dateTime
        )

    fun formatLocalDateTimeToHourOfDay(dateTime: LocalDateTime): String =
        DateTimeFormatter.ofPattern("HH").format(
            dateTime
        )

    fun getCurrentDayTimeState(
        hourOfDay: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    ): DayTimeState {
        return when (hourOfDay) {
            in 0..6 -> DayTimeState.DAWN
            in 7..11 -> DayTimeState.MORNING
            in 12..16 -> DayTimeState.NOON
            in 17..19 -> DayTimeState.EVENING
            in 20..23 -> DayTimeState.NIGHT
            else -> DayTimeState.MORNING
        }
    }

    /**
     * With the new R8 and ART de-sugaring features, It is okay to use kotlin enums in Android
     */
    enum class DayTimeState {
        DAWN, MORNING, NOON, EVENING, NIGHT
    }

}
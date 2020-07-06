package com.emami.openweather.common.util

import androidx.room.TypeConverter
import java.util.*

/**
 * Provides a way to work with [Date] in Room
 */
class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}
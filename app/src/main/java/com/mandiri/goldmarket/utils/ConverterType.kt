package com.mandiri.goldmarket.utils

import androidx.room.TypeConverter
import java.util.*

class ConverterType {
    @TypeConverter
    fun toDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}
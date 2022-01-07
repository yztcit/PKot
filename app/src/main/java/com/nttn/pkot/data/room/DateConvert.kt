package com.nttn.pkot.data.room

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConvert {
    private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

    @TypeConverter
    fun timestampToDate(timestamp: String?): Date? {
        if (timestamp == null) {
            return Date(System.currentTimeMillis())
        }
        synchronized(format) {
            return format.parse(timestamp)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String? {
        if (date == null) {
            val nowDate = Date(System.currentTimeMillis())
            synchronized(format) {
                return format.format(nowDate)
            }
        }
        synchronized(format) {
            return format.format(date)
        }
    }
}
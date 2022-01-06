package com.nttn.pkot.data.room

import androidx.room.TypeConverter
import java.util.*

object DataConvert {
    @TypeConverter
    fun timestampToDate(timestamp: Long?): Date? {
        return timestamp?.run {
            Date(this)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.run {
            time
        }
    }
}
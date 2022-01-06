package com.nttn.pkot.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Note::class], version = 1)
@TypeConverters(DataConvert::class)
abstract class PkotDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
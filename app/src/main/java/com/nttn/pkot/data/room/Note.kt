package com.nttn.pkot.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    var title: String?,
    var date: Date?,
    var type: String?,
    var content: String?
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
package com.nttn.pkot.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey var id: Int,
    var title: String?,
    var date: Date?,
    var content: String?
)
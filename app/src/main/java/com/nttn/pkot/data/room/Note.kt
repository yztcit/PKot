package com.nttn.pkot.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
class Note(@PrimaryKey var id: Int = 0, var title: String? = "标题", var date: Date? = Date(), var content: String? = null)
/*
* id
* title
* date
* type
* content
* */
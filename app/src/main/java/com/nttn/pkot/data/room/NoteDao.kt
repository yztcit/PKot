package com.nttn.pkot.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.util.*
import kotlin.collections.ArrayList

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): ArrayList<Note>

    @Query("SELECT * FROM notes WHERE date BETWEEN :dateFrom AND :dateTo")
    fun queryNoteBetweenDates(dateFrom: Date, dateTo: Date): ArrayList<Note>

    @Insert
    fun insertNotes(vararg note: Note)

    @Insert
    fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}
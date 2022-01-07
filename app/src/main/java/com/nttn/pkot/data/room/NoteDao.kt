package com.nttn.pkot.data.room

import androidx.room.*
import java.util.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<Note>?

    @Query("SELECT * FROM notes WHERE date BETWEEN :dateFrom AND :dateTo")
    fun queryNoteBetweenDates(dateFrom: Date, dateTo: Date): List<Note>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(vararg note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}
package com.example.noteapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    suspend fun getAll(): List<Note>

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun update(note: Note)

}
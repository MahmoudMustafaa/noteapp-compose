package com.example.noteapp.data.repository

import com.example.noteapp.data.db.Note
import com.example.noteapp.data.db.NoteDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor (val noteDao: NoteDao)  {



    suspend fun getAll(): List<Note> {
        return noteDao.getAll()
    }

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

}
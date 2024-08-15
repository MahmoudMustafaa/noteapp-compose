package com.example.noteapp.di

import android.content.Context
import androidx.room.Room
import com.example.noteapp.data.db.NoteDao
import com.example.noteapp.data.db.NoteDatabase
import com.example.noteapp.data.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabasModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
      return  Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "Note_database"
        ).build()

    }
    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase) :NoteDao {

        return noteDatabase.noteDao()
    }
    @Provides
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }
}


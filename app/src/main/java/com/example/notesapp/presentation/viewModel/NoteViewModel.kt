package com.example.noteapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.db.Note
import com.example.noteapp.data.db.NoteDatabase
import com.example.noteapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(val noteRepository: NoteRepository) : ViewModel() {

    private val _allNotes = MutableStateFlow<List<Note>?>(null)
    val allNotes : StateFlow<List<Note>?> get() = _allNotes



    init {
        getAllNotes()
    }

    fun getAllNotes() {
        viewModelScope.launch {
            _allNotes.value = noteRepository.getAll()
            println("All notes: ${_allNotes.value}")
        }
    }
    fun insert(note:Note){
        viewModelScope.launch {
            noteRepository.insert(note)
            getAllNotes()
        }
    }

    fun delete(note:Note){
        viewModelScope.launch {
            noteRepository.delete(note)
            getAllNotes()

        }
    }


    fun update(note:Note){
        viewModelScope.launch {
            noteRepository.update(note)
            getAllNotes()
        }
    }


}
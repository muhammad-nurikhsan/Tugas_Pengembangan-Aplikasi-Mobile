package com.muhammadnurikhsan.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import com.muhammadnurikhsan.myprofileapp.data.Note
import com.muhammadnurikhsan.myprofileapp.data.NotesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    fun addNote(title: String, content: String) {
        val newId = (_uiState.value.notes.maxOfOrNull { it.id } ?: 0) + 1
        val newNote = Note(id = newId, title = title, content = content)
        _uiState.update { it.copy(notes = it.notes + newNote) }
    }

    fun updateNote(id: Int, title: String, content: String) {
        _uiState.update { state ->
            state.copy(notes = state.notes.map { note ->
                if (note.id == id) note.copy(title = title, content = content) else note
            })
        }
    }

    fun toggleFavorite(id: Int) {
        _uiState.update { state ->
            state.copy(notes = state.notes.map { note ->
                if (note.id == id) note.copy(isFavorite = !note.isFavorite) else note
            })
        }
    }

    fun getNoteById(id: Int): Note? = _uiState.value.notes.find { it.id == id }

    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }
}
package com.muhammadnurikhsan.tugas9_pam.data

import com.muhammadnurikhsan.tugas9pam.db.NoteEntity

sealed class NotesUiState {
    object Loading : NotesUiState()
    object Empty   : NotesUiState()
    data class Content(val notes: List<NoteEntity>) : NotesUiState()
    data class Error(val message: String)           : NotesUiState()
}
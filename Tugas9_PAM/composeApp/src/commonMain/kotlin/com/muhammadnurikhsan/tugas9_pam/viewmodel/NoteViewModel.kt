package com.muhammadnurikhsan.tugas9_pam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammadnurikhsan.tugas9_pam.data.NoteRepository
import com.muhammadnurikhsan.tugas9_pam.data.NotesUiState
import com.muhammadnurikhsan.tugas9pam.db.NoteEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<NotesUiState> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) repository.getAllNotes()
            else repository.searchNotes(query)
        }
        .map { notes ->
            if (notes.isEmpty()) NotesUiState.Empty
            else NotesUiState.Content(notes)
        }
        .onStart { emit(NotesUiState.Loading) }
        .catch { e -> emit(NotesUiState.Error(e.message ?: "Unknown error")) }
        .stateIn(
            scope          = viewModelScope,
            started        = SharingStarted.WhileSubscribed(5_000),
            initialValue   = NotesUiState.Loading
        )

    val favoritesState: StateFlow<NotesUiState> = repository.getFavorites()
        .map { notes ->
            if (notes.isEmpty()) NotesUiState.Empty
            else NotesUiState.Content(notes)
        }
        .onStart { emit(NotesUiState.Loading) }
        .stateIn(
            scope        = viewModelScope,
            started      = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotesUiState.Loading
        )

    private val _selectedNoteId = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedNote: StateFlow<NoteEntity?> = _selectedNoteId
        .flatMapLatest { id ->
            if (id == null) flowOf(null)
            else repository.getNoteById(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun onSearchQueryChange(query: String) { _searchQuery.value = query }

    fun selectNote(id: Long) { _selectedNoteId.value = id }

    fun addNote(title: String, content: String) {
        viewModelScope.launch { repository.insertNote(title, content) }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch { repository.updateNote(id, title, content) }
    }

    fun toggleFavorite(id: Long) {
        viewModelScope.launch { repository.toggleFavorite(id) }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch { repository.deleteNote(id) }
    }
}

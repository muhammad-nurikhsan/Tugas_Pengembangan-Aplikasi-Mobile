package com.muhammadnurikhsan.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammadnurikhsan.myprofileapp.components.NoteCard
import com.muhammadnurikhsan.myprofileapp.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: NotesViewModel,
    onNoteClick: (Int) -> Unit
) {
    val uiState      by viewModel.uiState.collectAsState()
    val favoriteNotes = uiState.notes.filter { it.isFavorite }

    Scaffold(
        topBar = {
            TopAppBar(
                title  = { Text("Favorit", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        if (favoriteNotes.isEmpty()) {
            Box(
                modifier         = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = "Belum ada catatan favorit.\nTekan ikon hati pada catatan untuk menandainya.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier            = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding      = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favoriteNotes, key = { it.id }) { note ->
                    NoteCard(
                        note             = note,
                        onClick          = { onNoteClick(note.id) },
                        onFavoriteToggle = { viewModel.toggleFavorite(note.id) }
                    )
                }
            }
        }
    }
}
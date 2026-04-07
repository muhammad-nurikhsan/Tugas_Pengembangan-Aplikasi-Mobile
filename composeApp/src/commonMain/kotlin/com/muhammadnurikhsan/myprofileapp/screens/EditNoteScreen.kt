package com.muhammadnurikhsan.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammadnurikhsan.myprofileapp.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel,
    noteId: Int,
    onBack: () -> Unit,
    onSave: (Int, String, String) -> Unit
) {
    val note = viewModel.getNoteById(noteId)
    if (note == null) { onBack(); return }

    var title   by remember(note) { mutableStateOf(note.title) }
    var content by remember(note) { mutableStateOf(note.content) }

    Scaffold(
        topBar = {
            TopAppBar(
                title          = { Text("Edit Catatan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value         = title,
                onValueChange = { title = it },
                label         = { Text("Judul") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )
            OutlinedTextField(
                value         = content,
                onValueChange = { content = it },
                label         = { Text("Isi Catatan") },
                modifier      = Modifier.fillMaxWidth().weight(1f),
                singleLine    = false
            )
            Button(
                onClick  = { onSave(noteId, title.trim(), content.trim()) },
                modifier = Modifier.fillMaxWidth(),
                enabled  = title.isNotBlank() && content.isNotBlank()
            ) {
                Text("Simpan Perubahan", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
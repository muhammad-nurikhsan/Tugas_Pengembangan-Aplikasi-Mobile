package com.muhammadnurikhsan.tugas8_pam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadnurikhsan.tugas8_pam.data.NotesUiState
import com.muhammadnurikhsan.tugas8_pam.viewmodel.NoteViewModel

@Composable
fun FavoritesScreen(viewModel: NoteViewModel, onNoteClick: (Long) -> Unit) {
    val state by viewModel.favoritesState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F7F5))) {

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 28.dp)) {
            Text("starred", fontSize = 30.sp, fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A), letterSpacing = (-0.5).sp)
            Text("notes you love", fontSize = 13.sp, color = Color(0xFF888885))
        }

        when (state) {
            is NotesUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF1A1A1A), strokeWidth = 2.dp)
                }
            }
            is NotesUiState.Empty -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("—", fontSize = 32.sp, color = Color(0xFFDDDDD8))
                        Text(
                            "no starred notes yet\ntap the heart on a note",
                            textAlign  = TextAlign.Center,
                            color      = Color(0xFF888885),
                            fontSize   = 14.sp,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
            is NotesUiState.Content -> {
                LazyColumn(
                    contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier            = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        (state as NotesUiState.Content).notes,
                        key = { _, n -> n.id }
                    ) { _, note ->
                        Surface(
                            modifier = Modifier.fillMaxWidth().clickable { onNoteClick(note.id) },
                            shape    = RoundedCornerShape(16.dp),
                            color    = Color.White,
                            border   = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEDE8))
                        ) {
                            Row(
                                modifier          = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Filled.Favorite, null,
                                    tint     = Color(0xFFE5533D),
                                    modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(note.title, fontWeight = FontWeight.SemiBold,
                                        fontSize = 15.sp, color = Color(0xFF1A1A1A),
                                        maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    if (note.content.isNotBlank()) {
                                        Spacer(Modifier.height(3.dp))
                                        Text(note.content, fontSize = 13.sp,
                                            maxLines = 2, overflow = TextOverflow.Ellipsis,
                                            color = Color(0xFF888885))
                                    }
                                }
                            }
                        }
                    }
                    item { Spacer(Modifier.height(8.dp)) }
                }
            }
            else -> {}
        }
    }
}
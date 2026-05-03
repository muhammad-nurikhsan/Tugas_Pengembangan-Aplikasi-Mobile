package com.muhammadnurikhsan.tugas7_pam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadnurikhsan.tugas7_pam.data.NotesUiState
import com.muhammadnurikhsan.tugas7pam.db.NoteEntity
import com.muhammadnurikhsan.tugas7_pam.viewmodel.NoteViewModel
import kotlinx.datetime.Instant

private val Cream  = Color(0xFFF5F0E8)
private val Dark   = Color(0xFF111111)
private val Card   = Color(0xFF1A1A1A)
private val Border = Color(0xFF222222)
private val Muted  = Color(0xFF555555)
private val Hint   = Color(0xFF333333)

@Composable
fun NoteListScreen(
    viewModel: NoteViewModel,
    onNoteClick: (Long) -> Unit,
    onAddClick: () -> Unit
) {
    val uiState     by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Dark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.Bottom
        ) {
            Column {
                Text(
                    text       = "notes.",
                    fontSize   = 28.sp,
                    fontWeight = FontWeight.Medium,
                    color      = Color.White,
                    letterSpacing = (-0.5).sp
                )
                val count = if (uiState is NotesUiState.Content)
                    "${(uiState as NotesUiState.Content).notes.size} entries"
                else "— entries"
                Text(
                    text     = count,
                    fontSize = 12.sp,
                    color    = Muted,
                    letterSpacing = 0.5.sp
                )
            }
            Box(
                modifier         = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Card)
                    .border(0.5.dp, Border, CircleShape)
                    .clickable(onClick = onAddClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Filled.Add,
                    contentDescription = "Tambah",
                    tint               = Color.White,
                    modifier           = Modifier.size(18.dp)
                )
            }
        }

        OutlinedTextField(
            value         = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier      = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 12.dp),
            placeholder   = { Text("search notes...", color = Muted, fontSize = 13.sp) },
            leadingIcon   = {
                Icon(Icons.Filled.Search, null,
                    tint     = Muted,
                    modifier = Modifier.size(16.dp))
            },
            trailingIcon  = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                        Icon(Icons.Filled.Close, null, tint = Muted, modifier = Modifier.size(14.dp))
                    }
                }
            },
            singleLine = true,
            shape      = RoundedCornerShape(12.dp),
            colors     = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = Color(0xFF2A2A2A),
                unfocusedBorderColor = Border,
                focusedContainerColor   = Card,
                unfocusedContainerColor = Card,
                cursorColor             = Color.White,
                focusedTextColor        = Color.White,
                unfocusedTextColor      = Color.White,
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 13.sp)
        )

        when (val state = uiState) {
            is NotesUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White, strokeWidth = 1.dp, modifier = Modifier.size(24.dp))
                }
            }
            is NotesUiState.Empty -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text      = if (searchQuery.isBlank()) "nothing here yet.\ntap + to start writing."
                        else "no results for\n\"$searchQuery\"",
                        textAlign = TextAlign.Center,
                        color     = Muted,
                        fontSize  = 14.sp,
                        lineHeight = 22.sp
                    )
                }
            }
            is NotesUiState.Content -> {
                LazyColumn(
                    contentPadding      = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier            = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(state.notes, key = { _, note -> note.id }) { index, note ->
                        NoteCard(
                            note            = note,
                            isAccent        = index == 0,
                            onClick         = { onNoteClick(note.id) },
                            onToggleFavorite = { viewModel.toggleFavorite(note.id) },
                            onDelete        = { viewModel.deleteNote(note.id) }
                        )
                    }
                    item { Spacer(Modifier.height(8.dp)) }
                }
            }
            is NotesUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("error: ${state.message}", color = Color(0xFFE8776A), fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun NoteCard(
    note: NoteEntity,
    isAccent: Boolean,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    val bgColor    = if (isAccent) Cream else Card
    val titleColor = if (isAccent) Dark  else Color.White
    val bodyColor  = if (isAccent) Color(0xFF777777) else Muted
    val dateColor  = if (isAccent) Color(0xFFAAAAAA) else Hint
    val borderColor = if (isAccent) Color(0xFFE8E2D6) else Border

    val dateStr = remember(note.updated_at) {
        try {
            val instant = Instant.fromEpochMilliseconds(note.updated_at)
            instant.toString().take(10)
        } catch (e: Exception) { "" }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(0.5.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier          = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = note.title,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color      = titleColor,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text      = note.content,
                    fontSize  = 12.sp,
                    color     = bodyColor,
                    maxLines  = 2,
                    overflow  = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
                Spacer(Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier              = Modifier.fillMaxWidth()
                ) {
                    Text(text = dateStr, fontSize = 11.sp, color = dateColor, letterSpacing = 0.3.sp)
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick  = onToggleFavorite,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (note.is_favorite == 1L) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        tint     = if (note.is_favorite == 1L) Color(0xFFE8776A)
                        else if (isAccent) Color(0xFFAAAAAA) else Color(0xFF333333),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
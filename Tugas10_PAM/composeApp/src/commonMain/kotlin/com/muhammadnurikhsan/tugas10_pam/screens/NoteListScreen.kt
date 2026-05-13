package com.muhammadnurikhsan.tugas10_pam.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadnurikhsan.tugas10_pam.data.NotesUiState
import com.muhammadnurikhsan.tugas10_pam.platform.NetworkMonitor
import com.muhammadnurikhsan.tugas10_pam.util.TestTags
import com.muhammadnurikhsan.tugas10_pam.viewmodel.NoteViewModel
import com.muhammadnurikhsan.tugas10pam.db.NoteEntity
import kotlinx.datetime.Instant
import org.koin.compose.koinInject

private val cardColors = listOf(
    Color(0xFFFFFBF0),
    Color(0xFFF5F5F0),
    Color(0xFFF0F5F0),
    Color(0xFFF5F0F5),
    Color(0xFFF0F5FF),
)

@Composable
fun NoteListScreen(
    viewModel    : NoteViewModel,
    onNoteClick  : (Long) -> Unit,
    onAddClick   : () -> Unit,
    onAIChatClick: () -> Unit
) {
    val uiState     by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val networkMonitor: NetworkMonitor = koinInject()
    val isConnected by networkMonitor.observeConnectivity().collectAsState(initial = true)

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F7F5))) {

        AnimatedVisibility(visible = !isConnected,
            enter = slideInVertically(), exit = slideOutVertically()) {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFE5533D))
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Filled.WifiOff, null, tint = Color.White, modifier = Modifier.size(15.dp))
                Text("no internet connection", color = Color.White, fontSize = 13.sp)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 28.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Column {
                Text("my notes", fontSize = 30.sp, fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A), letterSpacing = (-0.5).sp)
                val count = if (uiState is NotesUiState.Content)
                    "${(uiState as NotesUiState.Content).notes.size} notes" else ""
                if (count.isNotEmpty()) {
                    Text(count, fontSize = 13.sp, color = Color(0xFF888885))
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .shadow(6.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color(0xFF2D6A4F))
                        .clickable(onClick = onAIChatClick)
                        .testTag(TestTags.AI_CHAT_BUTTON),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.AutoAwesome, null,
                        tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .shadow(6.dp, CircleShape)
                        .clip(CircleShape)
                        .background(Color(0xFF1A1A1A))
                        .clickable(onClick = onAddClick)
                        .testTag(TestTags.ADD_BUTTON),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Add, null,
                        tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }
        }

        OutlinedTextField(
            value         = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier      = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .testTag(TestTags.SEARCH_FIELD),
            placeholder   = { Text("search notes", color = Color(0xFFBBBBB5), fontSize = 14.sp) },
            leadingIcon   = { Icon(Icons.Filled.Search, null,
                tint = Color(0xFFBBBBB5), modifier = Modifier.size(18.dp)) },
            trailingIcon  = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                        Icon(Icons.Filled.Close, null,
                            tint = Color(0xFFBBBBB5), modifier = Modifier.size(16.dp))
                    }
                }
            },
            singleLine = true,
            shape      = RoundedCornerShape(14.dp),
            colors     = OutlinedTextFieldDefaults.colors(
                focusedBorderColor      = Color(0xFF1A1A1A),
                unfocusedBorderColor    = Color(0xFFE5E5E0),
                focusedContainerColor   = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor             = Color(0xFF1A1A1A),
                focusedTextColor        = Color(0xFF1A1A1A),
                unfocusedTextColor      = Color(0xFF1A1A1A),
            )
        )

        when (val state = uiState) {
            is NotesUiState.Loading -> {
                Box(
                    modifier         = Modifier.fillMaxSize().testTag(TestTags.LOADING_STATE),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF1A1A1A), strokeWidth = 2.dp)
                }
            }
            is NotesUiState.Empty -> {
                Box(
                    modifier         = Modifier.fillMaxSize().testTag(TestTags.EMPTY_STATE),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("—", fontSize = 32.sp, color = Color(0xFFDDDDD8))
                        Text(
                            if (searchQuery.isBlank()) "nothing here yet\ntap + to start writing"
                            else "no results for \"$searchQuery\"",
                            textAlign = TextAlign.Center, color = Color(0xFF888885),
                            fontSize = 14.sp, lineHeight = 22.sp
                        )
                    }
                }
            }
            is NotesUiState.Content -> {
                LazyColumn(
                    contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier            = Modifier.fillMaxSize().testTag(TestTags.NOTE_LIST)
                ) {
                    itemsIndexed(state.notes, key = { _, note -> note.id }) { index, note ->
                        NoteCard(
                            note             = note,
                            colorIndex       = index % cardColors.size,
                            onClick          = { onNoteClick(note.id) },
                            onToggleFavorite = { viewModel.toggleFavorite(note.id) },
                            onDelete         = { viewModel.deleteNote(note.id) }
                        )
                    }
                    item { Spacer(Modifier.height(8.dp)) }
                }
            }
            is NotesUiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("something went wrong", color = Color(0xFFE5533D), fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
private fun NoteCard(
    note            : NoteEntity,
    colorIndex      : Int,
    onClick         : () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete        : () -> Unit
) {
    val bg      = cardColors[colorIndex]
    val dateStr = remember(note.updated_at) {
        try { Instant.fromEpochMilliseconds(note.updated_at).toString().take(10) }
        catch (e: Exception) { "" }
    }

    Surface(
        modifier       = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(TestTags.NOTE_ITEM),
        shape          = RoundedCornerShape(16.dp),
        color          = bg,
        tonalElevation = 0.dp,
        border         = BorderStroke(1.dp, Color(0xFFE8E8E3))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Top
            ) {
                Text(
                    note.title,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = Color(0xFF1A1A1A),
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis,
                    modifier   = Modifier.weight(1f).testTag(TestTags.NOTE_TITLE)
                )
                Spacer(Modifier.width(8.dp))
                IconButton(
                    onClick  = onToggleFavorite,
                    modifier = Modifier.size(24.dp).testTag(TestTags.FAVORITE_BUTTON)
                ) {
                    Icon(
                        imageVector = if (note.is_favorite == 1L) Icons.Filled.Favorite
                        else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint     = if (note.is_favorite == 1L) Color(0xFFE5533D) else Color(0xFFCCCCC8),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            if (note.content.isNotBlank()) {
                Spacer(Modifier.height(6.dp))
                Text(note.content, fontSize = 13.sp, color = Color(0xFF666663),
                    maxLines = 3, overflow = TextOverflow.Ellipsis, lineHeight = 20.sp)
            }
            Spacer(Modifier.height(12.dp))
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(dateStr, fontSize = 11.sp, color = Color(0xFFAAAAAA))
                IconButton(
                    onClick  = onDelete,
                    modifier = Modifier.size(20.dp).testTag(TestTags.DELETE_BUTTON)
                ) {
                    Icon(Icons.Filled.Delete, null,
                        tint = Color(0xFFDDDDD8), modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}
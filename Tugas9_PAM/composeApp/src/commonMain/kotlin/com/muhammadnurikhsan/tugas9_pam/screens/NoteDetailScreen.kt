package com.muhammadnurikhsan.tugas9_pam.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadnurikhsan.tugas9_pam.data.SummarizeState
import com.muhammadnurikhsan.tugas9_pam.viewmodel.AIViewModel
import com.muhammadnurikhsan.tugas9_pam.viewmodel.NoteViewModel
import kotlinx.datetime.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    viewModel  : NoteViewModel,
    aiViewModel: AIViewModel,
    noteId     : Long,
    onBack     : () -> Unit,
    onEdit     : () -> Unit
) {
    LaunchedEffect(noteId) { viewModel.selectNote(noteId) }
    val note           by viewModel.selectedNote.collectAsState()
    val summarizeState by aiViewModel.summarizeState.collectAsState()

    DisposableEffect(Unit) {
        onDispose { aiViewModel.resetSummarize() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Catatan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    note?.let { n ->
                        IconButton(onClick = {
                            aiViewModel.summarizeNote(title = n.title, content = n.content)
                        }) {
                            Icon(
                                imageVector        = Icons.Filled.AutoAwesome,
                                contentDescription = "Rangkum dengan AI",
                                tint               = Color(0xFF2D6A4F)
                            )
                        }
                        IconButton(onClick = { viewModel.toggleFavorite(n.id) }) {
                            Icon(
                                imageVector = if (n.is_favorite == 1L) Icons.Filled.Favorite
                                else Icons.Filled.FavoriteBorder,
                                contentDescription = "Toggle Favorit",
                                tint = if (n.is_favorite == 1L) MaterialTheme.colorScheme.error
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                        IconButton(onClick = onEdit) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        if (note == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val n = note!!
            val dateStr = remember(n.updated_at) {
                try { Instant.fromEpochMilliseconds(n.updated_at).toString().take(10) }
                catch (e: Exception) { "" }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(n.title, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Text(dateStr, fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(n.content, fontSize = 16.sp, lineHeight = 26.sp)

                Spacer(Modifier.height(8.dp))
                SummarizeCard(state = summarizeState)
            }
        }
    }
}

@Composable
private fun SummarizeCard(state: SummarizeState) {
    AnimatedVisibility(
        visible = state !is SummarizeState.Idle,
        enter   = fadeIn() + expandVertically(),
        exit    = fadeOut() + shrinkVertically()
    ) {
        Surface(
            shape    = RoundedCornerShape(12.dp),
            color    = Color(0xFFE8F5E9),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    verticalAlignment      = Alignment.CenterVertically,
                    horizontalArrangement  = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(Icons.Filled.AutoAwesome, contentDescription = null,
                        tint = Color(0xFF2D6A4F), modifier = Modifier.size(14.dp))
                    Text("Ringkasan AI", fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold, color = Color(0xFF2D6A4F))
                }
                Spacer(Modifier.height(8.dp))
                when (state) {
                    is SummarizeState.Loading -> {
                        Row(
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(14.dp),
                                strokeWidth = 2.dp, color = Color(0xFF2D6A4F))
                            Text("Sedang merangkum...", fontSize = 13.sp, color = Color(0xFF555555))
                        }
                    }
                    is SummarizeState.Success -> {
                        Text(state.summary, fontSize = 14.sp,
                            color = Color(0xFF1A1A1A), lineHeight = 22.sp)
                    }
                    is SummarizeState.Error -> {
                        Text(state.message, fontSize = 13.sp, color = Color(0xFFE5533D))
                    }
                    else -> {}
                }
            }
        }
    }
}
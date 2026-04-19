package com.muhammadnurikhsan.tugas6_pam.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammadnurikhsan.tugas6_pam.ui.ArticlesViewModel
import com.muhammadnurikhsan.tugas6_pam.ui.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    articleId: Int,
    viewModel: ArticlesViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(articleId) {
        viewModel.loadArticleDetail(articleId)
    }

    val uiState by viewModel.selectedArticle.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Artikel") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            when (val state = uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is UiState.Success -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Article #${state.data.id} · User #${state.data.userId}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = state.data.title.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        HorizontalDivider()
                        Text(
                            text = state.data.body,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                is UiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Gagal memuat detail",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = state.message)
                        Button(onClick = { viewModel.loadArticleDetail(articleId) }) {
                            Text("Coba Lagi")
                        }
                    }
                }
            }
        }
    }
}
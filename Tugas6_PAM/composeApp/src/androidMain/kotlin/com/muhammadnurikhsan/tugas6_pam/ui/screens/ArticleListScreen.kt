package com.muhammadnurikhsan.tugas6_pam.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.muhammadnurikhsan.tugas6_pam.data.model.Article
import com.muhammadnurikhsan.tugas6_pam.ui.ArticlesViewModel
import com.muhammadnurikhsan.tugas6_pam.ui.UiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ArticleListScreen(
    viewModel: ArticlesViewModel,
    onArticleClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing = uiState is UiState.Loading

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refresh() }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News Reader") },
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pullRefresh(pullRefreshState)
        ) {
            when (val state = uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is UiState.Success -> {
                    LazyColumn {
                        items(state.data) { article ->
                            ArticleItem(
                                article = article,
                                onClick = { onArticleClick(article.id) }
                            )
                            HorizontalDivider()
                        }
                    }
                }
                is UiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Gagal memuat data",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = state.message)
                        Button(onClick = { viewModel.refresh() }) {
                            Text("Coba Lagi")
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun ArticleItem(article: Article, onClick: () -> Unit) {
    val imageUrl = "https://dummyjson.com/image/400x200?text=Article+${article.id}"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Article Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                if (article.tags.isNotEmpty()) {
                    Text(
                        text = article.tags.joinToString(" · ") { "#$it" },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = article.title.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.body.take(80) + "...",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}
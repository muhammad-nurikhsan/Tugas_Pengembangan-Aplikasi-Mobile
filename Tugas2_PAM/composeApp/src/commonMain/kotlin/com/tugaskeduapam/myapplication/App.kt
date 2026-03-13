package com.tugaskeduapam.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

data class News(
    val id: Int,
    val title: String,
    val category: String,
    val content: String
)

class NewsFeedViewModel {
    
    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList.asStateFlow()

    fun incrementReadCount() {
        _readCount.value++
    }

    fun addNews(news: News) {
        _newsList.value += news
    }

    fun clearNews() {
        _newsList.value = emptyList()
        _readCount.value = 0
    }

    fun getNewsFlow(): Flow<News> = flow {
        val categories = listOf("Teknologi", "Politik", "Cuaca")
        var id = 1

        while (currentCoroutineContext().isActive) {
            delay(2000)

            val news = News(
                id = id,
                title = "Berita $id",
                category = categories.random(),
                content = "Isi lengkap berita ke-$id"
            )

            emit(news)
            id++
        }
    }

    suspend fun fetchDetail(news: News): String =
        withContext(Dispatchers.IO) {
            delay(1000)
            "Detail Berita ${news.id}: ${news.content}"
        }
}

@Composable
fun App() {

    val viewModel = remember { NewsFeedViewModel() }
    val scope = rememberCoroutineScope()

    val newsList by viewModel.newsList.collectAsState()
    val readCount by viewModel.readCount.collectAsState()

    var selectedCategory by remember { mutableStateOf("Teknologi") }
    var isRunning by remember { mutableStateOf(false) }
    var detailText by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("News Feed Simulator", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Jumlah dibaca: $readCount")
        Text("Kategori aktif: $selectedCategory")

        Spacer(modifier = Modifier.height(12.dp))

        Row {

            Button(onClick = {
                isRunning = !isRunning
                if (!isRunning) viewModel.clearNews()
            }) {
                Text(if (isRunning) "Stop" else "Start")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                selectedCategory = when (selectedCategory) {
                    "Teknologi" -> "Politik"
                    "Politik" -> "Cuaca"
                    else -> "Teknologi"
                }
                viewModel.clearNews()
            }) {
                Text("Ganti Kategori")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {

            items(newsList) { news ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {

                                viewModel.incrementReadCount()

                                val deferred = async {
                                    viewModel.fetchDetail(news)
                                }

                                detailText = deferred.await()
                            }
                        }
                        .padding(8.dp)
                ) {
                    Text("[${news.category}] ${news.title}")
                }

                HorizontalDivider()
            }
        }

        detailText?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Detail:")
            Text(it)
        }
    }

    LaunchedEffect(isRunning, selectedCategory) {
        if (isRunning) {

            viewModel.getNewsFlow()
                .filter { it.category == selectedCategory }   // filter kategori
                .map { news ->                                 // transform data
                    news.copy(
                        title = "${news.title} (ID: ${news.id})"
                    )
                }
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    println("Error: ${e.message}")
                }
                .collect { news ->
                    viewModel.addNews(news)
                }
        }
    }
}

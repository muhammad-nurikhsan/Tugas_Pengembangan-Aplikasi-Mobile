package com.muhammadnurikhsan.tugas10_pam.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadnurikhsan.tugas10_pam.data.ChatMessage
import com.muhammadnurikhsan.tugas10_pam.viewmodel.AIViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIChatScreen(
    viewModel: AIViewModel,
    onBack   : () -> Unit
) {
    val uiState by viewModel.chatState.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope     = rememberCoroutineScope()

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            scope.launch { listState.animateScrollToItem(uiState.messages.size - 1) }
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(uiState.error) {
        uiState.error?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.dismissError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("AI Assistant", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        Text("Gemini 2.0 Flash", fontSize = 11.sp, color = Color(0xFF888885))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    if (uiState.messages.isNotEmpty()) {
                        IconButton(onClick = { viewModel.clearChat() }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Clear chat",
                                tint = Color(0xFF888885))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF7F7F5))
            )
        },
        containerColor = Color(0xFFF7F7F5)
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {

            LazyColumn(
                state          = listState,
                modifier       = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.messages.isEmpty()) {
                    item { WelcomeHint() }
                }
                items(uiState.messages) { message ->
                    ChatBubble(message = message)
                }
                if (uiState.isLoading) {
                    item { TypingIndicator() }
                }
            }

            ChatInputBar(
                text      = inputText,
                isLoading = uiState.isLoading,
                onChange  = { inputText = it },
                onSend    = {
                    viewModel.sendMessage(inputText.trim())
                    inputText = ""
                }
            )
        }
    }
}

@Composable
private fun WelcomeHint() {
    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("🤖", fontSize = 40.sp)
            Text("Halo! Saya asisten AI kamu.",
                fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1A1A1A))
            Text("Tanyakan apa saja soal catatan\natau hal lainnya.",
                fontSize = 13.sp, color = Color(0xFF888885), lineHeight = 20.sp,
                textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    val isUser      = message.isUser
    val bubbleColor = if (isUser) Color(0xFF1A1A1A) else Color.White
    val textColor   = if (isUser) Color.White else Color(0xFF1A1A1A)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier.size(28.dp).background(Color(0xFFE8E8E3), CircleShape),
                contentAlignment = Alignment.Center
            ) { Text("🤖", fontSize = 14.sp) }
            Spacer(Modifier.width(8.dp))
        }
        Surface(
            shape = RoundedCornerShape(
                topStart    = 16.dp, topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd   = if (isUser) 4.dp else 16.dp
            ),
            color           = bubbleColor,
            modifier        = Modifier.widthIn(max = 280.dp),
            shadowElevation = if (!isUser) 1.dp else 0.dp
        ) {
            Text(text = message.text, color = textColor, fontSize = 14.sp, lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp))
        }
    }
}

@Composable
private fun TypingIndicator() {
    Row(
        modifier = Modifier.padding(start = 36.dp, top = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp,
                bottomStart = 4.dp, bottomEnd = 16.dp),
            color = Color.White, shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    val infiniteTransition = rememberInfiniteTransition(label = "dot$index")
                    val alpha by infiniteTransition.animateFloat(
                        initialValue = 0.3f, targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(500), repeatMode = RepeatMode.Reverse,
                            initialStartOffset = StartOffset(index * 150)
                        ), label = "alpha$index"
                    )
                    Box(modifier = Modifier.size(6.dp).alpha(alpha)
                        .background(Color(0xFF888885), CircleShape))
                }
            }
        }
    }
}

@Composable
private fun ChatInputBar(
    text     : String,
    isLoading: Boolean,
    onChange : (String) -> Unit,
    onSend   : () -> Unit
) {
    Surface(color = Color.White, shadowElevation = 8.dp) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = text, onValueChange = onChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Tulis pesan...", fontSize = 14.sp, color = Color(0xFFBBBBB5)) },
                maxLines = 4, shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor      = Color(0xFF1A1A1A),
                    unfocusedBorderColor    = Color(0xFFE5E5E0),
                    focusedContainerColor   = Color(0xFFF7F7F5),
                    unfocusedContainerColor = Color(0xFFF7F7F5),
                    cursorColor             = Color(0xFF1A1A1A),
                    focusedTextColor        = Color(0xFF1A1A1A),
                    unfocusedTextColor      = Color(0xFF1A1A1A),
                )
            )
            Box(
                modifier = Modifier.size(44.dp).background(
                    if (text.isNotBlank() && !isLoading) Color(0xFF1A1A1A) else Color(0xFFDDDDD8),
                    CircleShape
                ),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp),
                        color = Color.White, strokeWidth = 2.dp)
                } else {
                    IconButton(onClick = onSend, enabled = text.isNotBlank(),
                        modifier = Modifier.fillMaxSize()) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Kirim",
                            tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}
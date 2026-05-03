package com.muhammadnurikhsan.tugas9_pam.data

import com.muhammadnurikhsan.tugas9_pam.network.GeminiContent
import com.muhammadnurikhsan.tugas9_pam.network.GeminiPart

data class ChatMessage(
    val text  : String,
    val isUser: Boolean
)

data class AIChatUiState(
    val messages : List<ChatMessage> = emptyList(),
    val isLoading: Boolean           = false,
    val error    : String?           = null
)

sealed class SummarizeState {
    object Idle                             : SummarizeState()
    object Loading                          : SummarizeState()
    data class Success(val summary: String) : SummarizeState()
    data class Error(val message: String)   : SummarizeState()
}

fun List<ChatMessage>.toGeminiHistory(): List<GeminiContent> =
    this.map { msg ->
        GeminiContent(
            role  = if (msg.isUser) "user" else "model",
            parts = listOf(GeminiPart(msg.text))
        )
    }
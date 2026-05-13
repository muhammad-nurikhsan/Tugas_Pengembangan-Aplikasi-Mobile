package com.muhammadnurikhsan.tugas10_pam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammadnurikhsan.tugas10_pam.data.AIChatUiState
import com.muhammadnurikhsan.tugas10_pam.data.ChatMessage
import com.muhammadnurikhsan.tugas10_pam.data.SummarizeState
import com.muhammadnurikhsan.tugas10_pam.data.toGeminiHistory
import com.muhammadnurikhsan.tugas10_pam.network.GeminiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AIViewModel(private val geminiService: GeminiService) : ViewModel() {

    private val _chatState = MutableStateFlow(AIChatUiState())
    val chatState: StateFlow<AIChatUiState> = _chatState.asStateFlow()

    fun sendMessage(userText: String) {
        println("DEBUG: sendMessage called with: $userText")
        if (userText.isBlank() || _chatState.value.isLoading) return

        val userMsg = ChatMessage(text = userText, isUser = true)
        _chatState.update { it.copy(
            messages  = it.messages + userMsg,
            isLoading = true,
            error     = null
        )}

        viewModelScope.launch {
            val history = _chatState.value.messages
                .dropLast(1)
                .toGeminiHistory()

            geminiService.chat(userMessage = userText, history = history)
                .onSuccess { reply ->
                    val aiMsg = ChatMessage(text = reply, isUser = false)
                    _chatState.update { it.copy(
                        messages  = it.messages + aiMsg,
                        isLoading = false
                    )}
                }
                .onFailure { error ->
                    _chatState.update { it.copy(
                        isLoading = false,
                        error     = error.message ?: "Terjadi kesalahan: ${error::class.simpleName}"
                    )}
                }
        }
    }

    fun clearChat() { _chatState.value = AIChatUiState() }

    fun dismissError() { _chatState.update { it.copy(error = null) } }

    private val _summarizeState = MutableStateFlow<SummarizeState>(SummarizeState.Idle)
    val summarizeState: StateFlow<SummarizeState> = _summarizeState.asStateFlow()

    fun summarizeNote(title: String, content: String) {
        if (content.isBlank() && title.isBlank()) return
        _summarizeState.value = SummarizeState.Loading

        viewModelScope.launch {
            geminiService.summarizeNote(title = title, content = content)
                .onSuccess { summary ->
                    _summarizeState.value = SummarizeState.Success(summary)
                }
                .onFailure { error ->
                    _summarizeState.value = SummarizeState.Error(
                        error.message ?: "Gagal merangkum catatan"
                    )
                }
        }
    }

    fun resetSummarize() { _summarizeState.value = SummarizeState.Idle }
}
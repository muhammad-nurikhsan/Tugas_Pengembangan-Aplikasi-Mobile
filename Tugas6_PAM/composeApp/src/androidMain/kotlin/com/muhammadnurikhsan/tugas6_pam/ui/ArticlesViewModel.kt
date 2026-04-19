package com.muhammadnurikhsan.tugas6_pam.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammadnurikhsan.tugas6_pam.data.model.Article
import com.muhammadnurikhsan.tugas6_pam.data.repository.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class ArticlesViewModel(
    private val repository: ArticleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState.asStateFlow()

    private val _selectedArticle = MutableStateFlow<UiState<Article>>(UiState.Loading)
    val selectedArticle: StateFlow<UiState<Article>> = _selectedArticle.asStateFlow()

    init {
        loadArticles()
    }

    fun loadArticles() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getArticles()
                .onSuccess { articles ->
                    _uiState.value = UiState.Success(articles)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(error.message ?: "Terjadi kesalahan")
                }
        }
    }

    fun loadArticleDetail(id: Int) {
        viewModelScope.launch {
            _selectedArticle.value = UiState.Loading
            repository.getArticleById(id)
                .onSuccess { article ->
                    _selectedArticle.value = UiState.Success(article)
                }
                .onFailure { error ->
                    _selectedArticle.value = UiState.Error(error.message ?: "Terjadi kesalahan")
                }
        }
    }

    fun refresh() { loadArticles() }
}
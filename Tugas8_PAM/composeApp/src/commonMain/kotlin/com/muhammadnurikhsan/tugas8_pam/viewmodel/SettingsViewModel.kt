package com.muhammadnurikhsan.tugas8_pam.viewmodel

import androidx.lifecycle.ViewModel
import com.muhammadnurikhsan.tugas8_pam.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
    val theme: String     = SettingsRepository.THEME_SYSTEM,
    val sortOrder: String = SettingsRepository.SORT_NEWEST,
    val isDarkMode: Boolean = false
)

class SettingsViewModel(private val repo: SettingsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SettingsUiState(
            theme      = repo.theme,
            sortOrder  = repo.sortOrder,
            isDarkMode = repo.isDarkMode
        )
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun setTheme(theme: String) {
        repo.theme = theme
        val dark = theme == SettingsRepository.THEME_DARK
        repo.isDarkMode = dark
        _uiState.update { it.copy(theme = theme, isDarkMode = dark) }
    }

    fun setSortOrder(sortOrder: String) {
        repo.sortOrder = sortOrder
        _uiState.update { it.copy(sortOrder = sortOrder) }
    }
}

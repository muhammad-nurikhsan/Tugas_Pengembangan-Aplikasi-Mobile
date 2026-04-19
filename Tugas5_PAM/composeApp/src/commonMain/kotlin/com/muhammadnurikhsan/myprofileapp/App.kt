package com.muhammadnurikhsan.myprofileapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.muhammadnurikhsan.myprofileapp.ui.ProfileScreen
import com.muhammadnurikhsan.myprofileapp.viewmodel.ProfileViewModel

@Composable
fun App() {
    // ViewModel dibuat di sini agar satu instance dipakai App + ProfileScreen
    val profileViewModel: ProfileViewModel = viewModel()
    val uiState by profileViewModel.uiState.collectAsState()

    // Dark mode theme — Bonus +10%
    // Theme berganti smooth saat isDarkMode berubah
    MaterialTheme(
        colorScheme = if (uiState.isDarkMode) darkColorScheme()
        else lightColorScheme()
    ) {
        ProfileScreen(profileViewModel = profileViewModel)
    }
}
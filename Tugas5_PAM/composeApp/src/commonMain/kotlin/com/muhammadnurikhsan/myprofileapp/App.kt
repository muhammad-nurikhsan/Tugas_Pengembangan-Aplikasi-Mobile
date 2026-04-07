package com.muhammadnurikhsan.myprofileapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.muhammadnurikhsan.myprofileapp.navigation.AppNavigation
import com.muhammadnurikhsan.myprofileapp.viewmodel.NotesViewModel

@Composable
fun App() {
    val notesViewModel: NotesViewModel = viewModel()
    val uiState by notesViewModel.uiState.collectAsState()

    val lightColors = lightColorScheme()
    val darkColors  = darkColorScheme()

    val background       by animateColorAsState(if (uiState.isDarkMode) darkColors.background       else lightColors.background,       tween(600), label = "bg")
    val surface          by animateColorAsState(if (uiState.isDarkMode) darkColors.surface          else lightColors.surface,          tween(600), label = "su")
    val primary          by animateColorAsState(if (uiState.isDarkMode) darkColors.primary          else lightColors.primary,          tween(600), label = "pr")
    val onSurface        by animateColorAsState(if (uiState.isDarkMode) darkColors.onSurface        else lightColors.onSurface,        tween(600), label = "os")
    val onBackground     by animateColorAsState(if (uiState.isDarkMode) darkColors.onBackground     else lightColors.onBackground,     tween(600), label = "ob")
    val primaryContainer by animateColorAsState(if (uiState.isDarkMode) darkColors.primaryContainer else lightColors.primaryContainer, tween(600), label = "pc")
    val onPrimaryContainer by animateColorAsState(if (uiState.isDarkMode) darkColors.onPrimaryContainer else lightColors.onPrimaryContainer, tween(600), label = "opc")
    val surfaceVariant   by animateColorAsState(if (uiState.isDarkMode) darkColors.surfaceVariant   else lightColors.surfaceVariant,   tween(600), label = "sv")
    val onSurfaceVariant by animateColorAsState(if (uiState.isDarkMode) darkColors.onSurfaceVariant else lightColors.onSurfaceVariant, tween(600), label = "osv")

    val animatedColorScheme = (if (uiState.isDarkMode) darkColors else lightColors).copy(
        background        = background,
        surface           = surface,
        primary           = primary,
        onSurface         = onSurface,
        onBackground      = onBackground,
        primaryContainer  = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        surfaceVariant    = surfaceVariant,
        onSurfaceVariant  = onSurfaceVariant
    )

    MaterialTheme(colorScheme = animatedColorScheme) {
        AppNavigation(viewModel = notesViewModel)
    }
}
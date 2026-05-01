package com.muhammadnurikhsan.tugas8_pam

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.muhammadnurikhsan.tugas8_pam.navigation.AppNavigation

private val AppColors = lightColorScheme(
    background           = Color(0xFFF7F7F5),
    surface              = Color(0xFFFFFFFF),
    surfaceVariant       = Color(0xFFF0EFE9),
    primary              = Color(0xFF1A1A1A),
    onPrimary            = Color(0xFFFFFFFF),
    onBackground         = Color(0xFF1A1A1A),
    onSurface            = Color(0xFF1A1A1A),
    onSurfaceVariant     = Color(0xFF888885),
    outline              = Color(0xFFE5E5E0),
    error                = Color(0xFFE5533D),
    onError              = Color(0xFFFFFFFF),
    secondary            = Color(0xFF4A4A4A),
    onSecondary          = Color(0xFFFFFFFF),
    secondaryContainer   = Color(0xFFEEEDE8),
    onSecondaryContainer = Color(0xFF1A1A1A),
    tertiary             = Color(0xFF2D6A4F),
    onTertiary           = Color(0xFFFFFFFF),
    primaryContainer     = Color(0xFFE8E8E3),
    onPrimaryContainer   = Color(0xFF1A1A1A),
)

@Composable
fun App() {
    MaterialTheme(colorScheme = AppColors) {
        AppNavigation()
    }
}
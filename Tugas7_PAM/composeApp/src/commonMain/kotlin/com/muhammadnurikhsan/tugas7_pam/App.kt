package com.muhammadnurikhsan.tugas7_pam

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.muhammadnurikhsan.tugas7_pam.data.NoteRepository
import com.muhammadnurikhsan.tugas7_pam.data.SettingsRepository
import com.muhammadnurikhsan.tugas7_pam.navigation.AppNavigation
import com.russhwolf.settings.Settings
import app.cash.sqldelight.db.SqlDriver
import com.muhammadnurikhsan.tugas7pam.db.NotesDatabase

private val AppDarkColors = darkColorScheme(
    background         = Color(0xFF111111),
    surface            = Color(0xFF1A1A1A),
    surfaceVariant     = Color(0xFF1E1E1E),
    primary            = Color(0xFFFFFFFF),
    onPrimary          = Color(0xFF111111),
    onBackground       = Color(0xFFE8E8E8),
    onSurface          = Color(0xFFE8E8E8),
    onSurfaceVariant   = Color(0xFF888888),
    outline            = Color(0xFF2A2A2A),
    error              = Color(0xFFE8776A),
    onError            = Color(0xFF111111),
    secondary          = Color(0xFFF5F0E8),
    onSecondary        = Color(0xFF111111),
    secondaryContainer = Color(0xFF222222),
    onSecondaryContainer = Color(0xFF888888),
    tertiary           = Color(0xFF444444),
    onTertiary         = Color(0xFFE8E8E8),
)

@Composable
fun App(driver: SqlDriver) {
    val database     = remember { NotesDatabase(driver) }
    val noteRepo     = remember { NoteRepository(database) }
    val settings     = remember { Settings() }
    val settingsRepo = remember { SettingsRepository(settings) }

    MaterialTheme(colorScheme = AppDarkColors) {
        AppNavigation(
            noteRepository     = noteRepo,
            settingsRepository = settingsRepo
        )
    }
}
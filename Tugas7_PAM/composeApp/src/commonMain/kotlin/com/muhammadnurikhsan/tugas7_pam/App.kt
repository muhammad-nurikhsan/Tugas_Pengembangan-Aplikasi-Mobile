package com.muhammadnurikhsan.tugas7_pam

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.muhammadnurikhsan.tugas7_pam.data.NoteRepository
import com.muhammadnurikhsan.tugas7_pam.data.SettingsRepository
import com.muhammadnurikhsan.tugas7pam.db.NotesDatabase
import com.muhammadnurikhsan.tugas7_pam.navigation.AppNavigation
import com.russhwolf.settings.Settings
import app.cash.sqldelight.db.SqlDriver

@Composable
fun App(driver: SqlDriver) {
    val database     = remember { NotesDatabase(driver) }
    val noteRepo     = remember { NoteRepository(database) }
    val settings     = remember { Settings() }
    val settingsRepo = remember { SettingsRepository(settings) }

    MaterialTheme {
        AppNavigation(
            noteRepository     = noteRepo,
            settingsRepository = settingsRepo
        )
    }
}
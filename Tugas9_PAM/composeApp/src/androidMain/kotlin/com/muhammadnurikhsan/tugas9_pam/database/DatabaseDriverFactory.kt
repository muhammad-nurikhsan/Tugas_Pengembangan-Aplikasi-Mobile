package com.muhammadnurikhsan.tugas9_pam.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.muhammadnurikhsan.tugas9pam.db.NotesDatabase

class DatabaseDriverFactory(private val context: Context) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema  = NotesDatabase.Schema,
            context = context,
            name    = "notes.db"
        )
    }
}
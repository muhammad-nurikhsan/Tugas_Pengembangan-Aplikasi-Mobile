package com.muhammadnurikhsan.tugas9_pam.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.muhammadnurikhsan.tugas9_pam.platform.appContext
import com.muhammadnurikhsan.tugas9pam.db.NotesDatabase
import org.koin.dsl.module

val androidModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema  = NotesDatabase.Schema,
            context = appContext,
            name    = "notes.db"
        )
    }
}
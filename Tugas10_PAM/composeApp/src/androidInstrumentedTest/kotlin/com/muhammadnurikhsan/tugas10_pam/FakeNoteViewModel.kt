package com.muhammadnurikhsan.tugas10_pam

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.muhammadnurikhsan.tugas10_pam.data.NoteRepository
import com.muhammadnurikhsan.tugas10_pam.platform.BatteryInfo
import com.muhammadnurikhsan.tugas10_pam.platform.NetworkMonitor
import com.muhammadnurikhsan.tugas10_pam.viewmodel.NoteViewModel
import com.muhammadnurikhsan.tugas10pam.db.NotesDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

fun setupKoinForTest() {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    try { stopKoin() } catch (e: Exception) { }
    startKoin {
        androidContext(context)
        modules(module {
            single { NetworkMonitor() }
            single { BatteryInfo() }
        })
    }
}

fun createTestViewModel(): NoteViewModel {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    val driver = AndroidSqliteDriver(
        schema  = NotesDatabase.Schema,
        context = context,
        name    = null
    )
    val database = NotesDatabase(driver)
    val repository = NoteRepository(database)
    return NoteViewModel(repository)
}
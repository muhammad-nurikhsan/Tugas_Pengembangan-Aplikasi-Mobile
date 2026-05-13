package com.muhammadnurikhsan.tugas10_pam.di

import com.muhammadnurikhsan.tugas10_pam.data.NoteRepository
import com.muhammadnurikhsan.tugas10_pam.data.SettingsRepository
import com.muhammadnurikhsan.tugas10_pam.network.GeminiService
import com.muhammadnurikhsan.tugas10_pam.platform.BatteryInfo
import com.muhammadnurikhsan.tugas10_pam.platform.DeviceInfo
import com.muhammadnurikhsan.tugas10_pam.platform.NetworkMonitor
import com.muhammadnurikhsan.tugas10_pam.viewmodel.AIViewModel
import com.muhammadnurikhsan.tugas10_pam.viewmodel.NoteViewModel
import com.muhammadnurikhsan.tugas10_pam.viewmodel.SettingsViewModel
import com.muhammadnurikhsan.tugas10pam.db.NotesDatabase
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val dataModule = module {
    single { DeviceInfo() }
    single { NetworkMonitor() }
    single { BatteryInfo() }
    single { Settings() }
    single { SettingsRepository(get()) }
    single { NotesDatabase(get()) }
    single { NoteRepository(get()) }
    single { GeminiService() }
}

val viewModelModule = module {
    factory { NoteViewModel(get()) }
    factory { SettingsViewModel(get()) }
    factory { AIViewModel(get()) }
}

val appModule = listOf(dataModule, viewModelModule)
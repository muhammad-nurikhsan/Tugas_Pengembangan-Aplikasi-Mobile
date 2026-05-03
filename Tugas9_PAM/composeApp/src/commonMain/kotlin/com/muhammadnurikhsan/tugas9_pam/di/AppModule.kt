package com.muhammadnurikhsan.tugas9_pam.di

import com.muhammadnurikhsan.tugas9_pam.data.NoteRepository
import com.muhammadnurikhsan.tugas9_pam.data.SettingsRepository
import com.muhammadnurikhsan.tugas9_pam.network.GeminiService
import com.muhammadnurikhsan.tugas9_pam.platform.BatteryInfo
import com.muhammadnurikhsan.tugas9_pam.platform.DeviceInfo
import com.muhammadnurikhsan.tugas9_pam.platform.NetworkMonitor
import com.muhammadnurikhsan.tugas9_pam.viewmodel.AIViewModel
import com.muhammadnurikhsan.tugas9_pam.viewmodel.NoteViewModel
import com.muhammadnurikhsan.tugas9_pam.viewmodel.SettingsViewModel
import com.muhammadnurikhsan.tugas9pam.db.NotesDatabase
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val appModule = module {
    single { DeviceInfo() }
    single { NetworkMonitor() }
    single { BatteryInfo() }
    single { Settings() }
    single { SettingsRepository(get()) }
    single { NotesDatabase(get()) }
    single { NoteRepository(get()) }
    single { GeminiService() }
    factory { NoteViewModel(get()) }
    factory { SettingsViewModel(get()) }
    factory { AIViewModel(get()) }
}
package com.muhammadnurikhsan.tugas8_pam.di

import com.muhammadnurikhsan.tugas8_pam.data.NoteRepository
import com.muhammadnurikhsan.tugas8_pam.data.SettingsRepository
import com.muhammadnurikhsan.tugas8_pam.platform.BatteryInfo
import com.muhammadnurikhsan.tugas8_pam.platform.DeviceInfo
import com.muhammadnurikhsan.tugas8_pam.platform.NetworkMonitor
import com.muhammadnurikhsan.tugas8_pam.viewmodel.NoteViewModel
import com.muhammadnurikhsan.tugas8_pam.viewmodel.SettingsViewModel
import com.muhammadnurikhsan.tugas8pam.db.NotesDatabase
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
    factory { NoteViewModel(get()) }
    factory { SettingsViewModel(get()) }
}
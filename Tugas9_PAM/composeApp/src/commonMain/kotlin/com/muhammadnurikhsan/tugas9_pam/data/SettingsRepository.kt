package com.muhammadnurikhsan.tugas9_pam.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class SettingsRepository(private val settings: Settings) {

    companion object {
        const val KEY_THEME      = "app_theme"
        const val KEY_SORT_ORDER = "sort_order"
        const val KEY_DARK_MODE  = "dark_mode"

        const val THEME_SYSTEM = "system"
        const val THEME_LIGHT  = "light"
        const val THEME_DARK   = "dark"

        const val SORT_NEWEST = "newest"
        const val SORT_OLDEST = "oldest"
        const val SORT_TITLE  = "title"
    }

    var theme: String
        get() = settings[KEY_THEME, THEME_SYSTEM]
        set(value) { settings[KEY_THEME] = value }

    var sortOrder: String
        get() = settings[KEY_SORT_ORDER, SORT_NEWEST]
        set(value) { settings[KEY_SORT_ORDER] = value }

    var isDarkMode: Boolean
        get() = settings[KEY_DARK_MODE, false]
        set(value) { settings[KEY_DARK_MODE] = value }
}
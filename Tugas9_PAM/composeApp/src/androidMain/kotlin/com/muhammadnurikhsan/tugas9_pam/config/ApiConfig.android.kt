package com.muhammadnurikhsan.tugas9_pam.config

import com.muhammadnurikhsan.tugas9_pam.BuildConfig

actual object ApiConfig {
    actual val geminiApiKey: String = BuildConfig.GEMINI_API_KEY
}
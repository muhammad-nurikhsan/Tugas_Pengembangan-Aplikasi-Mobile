package com.muhammadnurikhsan.tugas10_pam.config

import com.muhammadnurikhsan.tugas10_pam.BuildConfig

actual object ApiConfig {
    actual val geminiApiKey: String = BuildConfig.GEMINI_API_KEY
}
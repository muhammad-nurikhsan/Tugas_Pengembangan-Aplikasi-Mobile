package com.muhammadnurikhsan.tugas9_pam.network

sealed class AIError : Exception() {
    data class RateLimited(val retryAfterSeconds: Int = 60) : AIError() {
        override val message = "Terlalu banyak permintaan. Coba lagi dalam $retryAfterSeconds detik."
    }
    data class Unauthorized(override val message: String = "API key tidak valid.") : AIError()
    data class ServerError(override val message: String = "Server AI sedang bermasalah.") : AIError()
    data class NetworkError(override val message: String = "Tidak ada koneksi internet.") : AIError()
    data class ParseError(override val message: String = "Gagal memproses respons AI.") : AIError()
    data class EmptyResponse(override val message: String = "AI tidak memberikan respons.") : AIError()
}
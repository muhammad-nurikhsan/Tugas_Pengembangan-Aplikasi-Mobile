package com.muhammadnurikhsan.tugas10_pam.platform

import kotlinx.coroutines.flow.Flow

expect class NetworkMonitor() {
    fun isConnected(): Boolean
    fun observeConnectivity(): Flow<Boolean>
}
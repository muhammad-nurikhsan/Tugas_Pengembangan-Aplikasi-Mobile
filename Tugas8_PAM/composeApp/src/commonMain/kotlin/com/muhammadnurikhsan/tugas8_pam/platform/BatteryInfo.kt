package com.muhammadnurikhsan.tugas8_pam.platform

expect class BatteryInfo() {
    fun getBatteryLevel(): Int
    fun isCharging(): Boolean
}
package com.muhammadnurikhsan.tugas9_pam.platform

expect class BatteryInfo() {
    fun getBatteryLevel(): Int
    fun isCharging(): Boolean
}
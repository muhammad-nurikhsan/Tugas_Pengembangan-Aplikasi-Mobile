package com.muhammadnurikhsan.tugas10_pam.platform

expect class BatteryInfo() {
    fun getBatteryLevel(): Int
    fun isCharging(): Boolean
}
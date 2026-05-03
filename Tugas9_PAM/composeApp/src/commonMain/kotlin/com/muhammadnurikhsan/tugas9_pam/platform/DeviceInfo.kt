package com.muhammadnurikhsan.tugas9_pam.platform

expect class DeviceInfo() {
    fun getDeviceName(): String
    fun getOsVersion(): String
    fun getAppVersion(): String
    fun isTablet(): Boolean
}
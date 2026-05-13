package com.muhammadnurikhsan.tugas10_pam.platform

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

actual class BatteryInfo actual constructor() {

    private val bm: BatteryManager by lazy {
        appContext.getSystemService(BatteryManager::class.java)
    }

    actual fun getBatteryLevel(): Int =
        bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

    actual fun isCharging(): Boolean {
        val intent = appContext.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL
    }
}
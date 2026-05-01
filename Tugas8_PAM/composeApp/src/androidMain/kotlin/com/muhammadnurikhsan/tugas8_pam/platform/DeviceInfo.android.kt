package com.muhammadnurikhsan.tugas8_pam.platform

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.pow
import kotlin.math.sqrt

lateinit var appContext: Context

actual class DeviceInfo actual constructor() {

    actual fun getDeviceName(): String =
        "${Build.MANUFACTURER} ${Build.MODEL}"

    actual fun getOsVersion(): String =
        "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"

    actual fun getAppVersion(): String = try {
        val pInfo = appContext.packageManager.getPackageInfo(appContext.packageName, 0)
        pInfo.versionName ?: "1.0"
    } catch (e: Exception) { "1.0" }

    actual fun isTablet(): Boolean {
        val metrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        val wm = appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        @Suppress("DEPRECATION")
        wm.defaultDisplay.getMetrics(metrics)
        val widthInch  = metrics.widthPixels  / metrics.xdpi
        val heightInch = metrics.heightPixels / metrics.ydpi
        return kotlin.math.sqrt(widthInch.pow(2) + heightInch.pow(2)) >= 7.0
    }
}
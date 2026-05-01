package com.muhammadnurikhsan.tugas8_pam

import android.app.Application
import com.muhammadnurikhsan.tugas8_pam.di.androidModule
import com.muhammadnurikhsan.tugas8_pam.di.appModule
import com.muhammadnurikhsan.tugas8_pam.platform.appContext
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(appModule, androidModule)
        }
    }
}
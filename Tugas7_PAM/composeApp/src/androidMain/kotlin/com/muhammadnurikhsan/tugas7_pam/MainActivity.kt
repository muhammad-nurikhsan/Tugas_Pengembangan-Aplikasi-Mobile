package com.muhammadnurikhsan.tugas7_pam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.muhammadnurikhsan.tugas7_pam.database.DatabaseDriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(driver = DatabaseDriverFactory(this).createDriver())
        }
    }
}
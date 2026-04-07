package com.muhammadnurikhsan.myaplication

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tugas1PAM",
    ) {
        App()
    }
}
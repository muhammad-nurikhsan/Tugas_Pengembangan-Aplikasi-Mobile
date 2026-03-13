package com.tugaskeduapam.myapplication

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tugas2PAM",
    ) {
        App()
    }
}
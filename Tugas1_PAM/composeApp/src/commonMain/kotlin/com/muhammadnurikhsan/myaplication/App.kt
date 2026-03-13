package com.muhammadnurikhsan.myaplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun App() {
    MaterialTheme {
        var showContent by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Halo, Muhammad Nurikhsan!")

            Text("NIM: 123140057")

            Text("Platform: Desktop")

            Button(
                onClick = { showContent = !showContent }
            ) {
                Text("Click me!")
            }

            AnimatedVisibility(showContent) {
                Text("Kamu klik tombolnya!")
            }
        }
    }
}
package com.muhammadnurikhsan.tugas10_pam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadnurikhsan.tugas10_pam.viewmodel.SettingsViewModel

@Composable
fun ProfileScreen(settingsViewModel: SettingsViewModel) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F7F5))) {

        // Header — clean, no gradient
        Column(
            modifier            = Modifier.fillMaxWidth().background(Color.White)
                .border(androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEDE8)),)
                .padding(horizontal = 20.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(80.dp).clip(CircleShape)
                    .background(Color(0xFF1A1A1A)),
                contentAlignment = Alignment.Center
            ) {
                Text("MN", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            Spacer(Modifier.height(16.dp))
            Text("Muhammad Nurikhsan", fontSize = 20.sp,
                fontWeight = FontWeight.Bold, color = Color(0xFF1A1A1A))
            Spacer(Modifier.height(4.dp))
            Text("123140057", fontSize = 13.sp, color = Color(0xFF888885))
            Spacer(Modifier.height(4.dp))
            Text("Teknik Informatika · ITERA", fontSize = 13.sp, color = Color(0xFF888885))
        }

        Column(
            modifier            = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SectionLabel("About")
            ProfileCard {
                ProfileRow("Program",  "Teknik Informatika")
                Divider()
                ProfileRow("Campus",   "Institut Teknologi Sumatera")
                Divider()
                ProfileRow("NIM",      "123140057")
            }

            SectionLabel("App Info")
            ProfileCard {
                ProfileRow("Name",     "Notes App")
                Divider()
                ProfileRow("Version",  "2.0")
                Divider()
                ProfileRow("Course",   "PAM Week 8")
                Divider()
                ProfileRow("Platform", "Kotlin Multiplatform")
            }

            SectionLabel("Built With")
            ProfileCard {
                ProfileRow("Database",  "SQLDelight")
                Divider()
                ProfileRow("DI",        "Koin")
                Divider()
                ProfileRow("UI",        "Compose Multiplatform")
                Divider()
                ProfileRow("Async",     "Coroutines + Flow")
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(text, fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
        color = Color(0xFF888885), letterSpacing = 0.8.sp,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
}

@Composable
private fun ProfileCard(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        color    = Color.White,
        border   = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEDE8))
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), content = content)
    }
}

@Composable
private fun ProfileRow(label: String, value: String) {
    Row(
        modifier              = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 14.sp, color = Color(0xFF1A1A1A))
        Text(value, fontSize = 13.sp, color = Color(0xFF888885))
    }
}

@Composable
private fun Divider() {
    HorizontalDivider(color = Color(0xFFF0EFE9), thickness = 1.dp)
}
package com.muhammadnurikhsan.tugas7_pam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadnurikhsan.tugas7_pam.data.NotesUiState
import com.muhammadnurikhsan.tugas7_pam.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(settingsViewModel: SettingsViewModel) {
    // Profil screen sekarang menerima SettingsViewModel untuk membaca state notes count
    // Namun karena NoteViewModel tidak di-pass di sini untuk kesederhanaan,
    // kita tampilkan profil statis dengan info mahasiswa

    Scaffold(
        topBar = {
            TopAppBar(
                title  = { Text("Profil", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier         = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("M", fontSize = 40.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

            Text("Muhammad Nurikhsan", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Mahasiswa Teknik Informatika — ITERA",
                fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
            Text("NIM: 123140057",
                fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            Card(
                modifier  = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier            = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Tentang Aplikasi", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    HorizontalDivider()
                    Text("Notes App v2.0", fontSize = 14.sp)
                    Text("Data tersimpan secara lokal (Offline-first)",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Tugas Praktikum Minggu 7 — PAM ITERA",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Powered by SQLDelight + Multiplatform Settings",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

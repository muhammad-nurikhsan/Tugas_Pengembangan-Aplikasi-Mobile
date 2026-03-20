package com.muhammadnurikhsan.myprofileapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.muhammadnurikhsan.myprofileapp.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel()
) {
    // Observe StateFlow dari ViewModel sebagai Compose State
    val uiState by profileViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // ← tambah ini
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {

        //  DARK MODE TOGGLE
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (uiState.isDarkMode) "Mode Gelap" else "Mode Terang",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Switch(
                checked = uiState.isDarkMode,
                onCheckedChange = { profileViewModel.toggleDarkMode() }
            )
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

        // PROFILE HEADER
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar circular
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.name.first().toString(),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nama — otomatis update saat state berubah (recomposition)
            Text(
                text = uiState.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = uiState.title,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = uiState.bio,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(12.dp))

        // TOMBOL EDIT PROFILE
        Button(
            onClick = { profileViewModel.toggleEditMode() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = if (uiState.isEditMode)
                    Icons.Default.Info else Icons.Default.Settings,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (uiState.isEditMode) "Tutup Form" else "Edit Profile")
        }

        Spacer(modifier = Modifier.height(8.dp))

        //  FORM EDIT — AnimatedVisibility
        AnimatedVisibility(visible = uiState.isEditMode) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                EditProfileForm(
                    currentName = uiState.name,
                    currentBio = uiState.bio,
                    onSave = { name, bio ->
                        profileViewModel.saveProfile(name, bio)
                    },
                    onCancel = { profileViewModel.toggleEditMode() }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        //  INFO CARD
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                InfoItem(
                    icon = Icons.Default.Person,
                    label = "Email",
                    value = uiState.email
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                InfoItem(
                    icon = Icons.Default.Home,
                    label = "Phone",
                    value = uiState.phone
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                InfoItem(
                    icon = Icons.Default.Info,
                    label = "Location",
                    value = uiState.location
                )
            }
        }
    }
}

// Stateless composable untuk tiap baris info
@Composable
fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
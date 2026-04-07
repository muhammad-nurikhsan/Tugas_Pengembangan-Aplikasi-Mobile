package com.muhammadnurikhsan.myprofileapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muhammadnurikhsan.myprofileapp.components.InfoItem
import com.muhammadnurikhsan.myprofileapp.components.ProfileHeader
import com.muhammadnurikhsan.myprofileapp.components.SocialButton

@Composable
fun ProfileScreen() {
    var showInfo by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        ProfileHeader(
            name = "Muhammad Nurikhsan",
            nim = "123140057",
            title = "Mahasiswa Teknik Informatika - ITERA",
            bio = "Saya adalah mahasiswa semester 6 yang memiliki interest di bidang AI dan Data Engineer"
        )

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { showInfo = !showInfo },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            Icon(
                imageVector = if (showInfo) Icons.Default.VisibilityOff
                else Icons.Default.Visibility,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (showInfo) "Sembunyikan Info" else "Tampilkan Info")
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(visible = showInfo) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    InfoItem(
                        icon = Icons.Default.Email,
                        label = "Email",
                        value = "muhammad.123140057@student.itera.ac.id"
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    InfoItem(
                        icon = Icons.Default.Phone,
                        label = "Phone",
                        value = "+62 812-1238-8101"
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    InfoItem(
                        icon = Icons.Default.LocationOn,
                        label = "Location",
                        value = "Bandar Lampung, Indonesia"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                SocialButton(icon = Icons.Default.Share, label = "Share")
            }
            Box(modifier = Modifier.weight(1f)) {
                SocialButton(icon = Icons.Default.Edit, label = "Edit Profile")
            }
        }
    }
}

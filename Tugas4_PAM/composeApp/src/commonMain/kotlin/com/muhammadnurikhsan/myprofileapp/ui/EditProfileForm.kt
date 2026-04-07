package com.muhammadnurikhsan.myprofileapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabeledTextField(
    label: String,
    value: String,                      // state turun dari parent ↓
    onValueChange: (String) -> Unit,    // event naik ke parent ↑
    singleLine: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = singleLine,
        modifier = Modifier.fillMaxWidth()
    )
}

// Form edit profile
// Menerima data dari parent dan callback untuk save/cancel
@Composable
fun EditProfileForm(
    currentName: String,
    currentBio: String,
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit
) {
    // State lokal sementara untuk form
    var nameInput by remember(currentName) { mutableStateOf(currentName) }
    var bioInput by remember(currentBio) { mutableStateOf(currentBio) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Edit Profile",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        HorizontalDivider()

        // Komponen LabeledTextField dipakai 2x — reusable karena stateless
        LabeledTextField(
            label = "Nama",
            value = nameInput,
            onValueChange = { nameInput = it }
        )

        LabeledTextField(
            label = "Bio",
            value = bioInput,
            onValueChange = { bioInput = it },
            singleLine = false
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Batal")
            }

            Button(
                onClick = { onSave(nameInput, bioInput) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Simpan")
            }
        }
    }
}
package com.muhammadnurikhsan.tugas9_pam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muhammadnurikhsan.tugas9_pam.data.SettingsRepository
import com.muhammadnurikhsan.tugas9_pam.platform.BatteryInfo
import com.muhammadnurikhsan.tugas9_pam.platform.DeviceInfo
import com.muhammadnurikhsan.tugas9_pam.platform.NetworkMonitor
import com.muhammadnurikhsan.tugas9_pam.viewmodel.SettingsViewModel
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val uiState        by viewModel.uiState.collectAsState()
    val deviceInfo: DeviceInfo         = koinInject()
    val networkMonitor: NetworkMonitor = koinInject()
    val batteryInfo: BatteryInfo       = koinInject()
    val isConnected  by networkMonitor.observeConnectivity().collectAsState(initial = true)
    val batteryLevel = batteryInfo.getBatteryLevel()
    val isCharging   = batteryInfo.isCharging()

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF7F7F5))) {

        // Header
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 28.dp)) {
            Text("settings", fontSize = 30.sp, fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A), letterSpacing = (-0.5).sp)
            Text("preferences & info", fontSize = 13.sp, color = Color(0xFF888885))
        }

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Device Info
            SectionLabel("Device Info")
            SettingsCard {
                DeviceRow("Device",  deviceInfo.getDeviceName())
                Divider()
                DeviceRow("OS",      deviceInfo.getOsVersion())
                Divider()
                DeviceRow("App",     "v${deviceInfo.getAppVersion()}")
                Divider()
                DeviceRow("Screen",  if (deviceInfo.isTablet()) "Tablet" else "Smartphone")
            }

            // Status
            SectionLabel("Status")
            SettingsCard {
                Row(
                    modifier              = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text("Internet", fontSize = 14.sp, color = Color(0xFF1A1A1A))
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isConnected) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                    ) {
                        Text(
                            if (isConnected) "Online" else "Offline",
                            fontSize   = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color      = if (isConnected) Color(0xFF2E7D32) else Color(0xFFE5533D),
                            modifier   = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
                Divider()
                Row(
                    modifier              = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text("Battery", fontSize = 14.sp, color = Color(0xFF1A1A1A))
                    Text(
                        "$batteryLevel%${if (isCharging) " · charging" else ""}",
                        fontSize  = 13.sp,
                        color     = Color(0xFF888885)
                    )
                }
                Spacer(Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress   = { batteryLevel / 100f },
                    modifier   = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                    color      = when {
                        batteryLevel > 50 -> Color(0xFF2D6A4F)
                        batteryLevel > 20 -> Color(0xFFE6A817)
                        else              -> Color(0xFFE5533D)
                    },
                    trackColor = Color(0xFFE5E5E0)
                )
            }

            // Theme
            SectionLabel("Theme")
            SettingsCard {
                listOf(
                    SettingsRepository.THEME_SYSTEM to "Follow System",
                    SettingsRepository.THEME_LIGHT  to "Light",
                    SettingsRepository.THEME_DARK   to "Dark"
                ).forEachIndexed { i, (value, label) ->
                    if (i > 0) Divider()
                    Row(
                        modifier          = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = uiState.theme == value,
                            onClick  = { viewModel.setTheme(value) },
                            colors   = RadioButtonDefaults.colors(selectedColor = Color(0xFF1A1A1A))
                        )
                        Text(label, fontSize = 14.sp, color = Color(0xFF1A1A1A))
                    }
                }
            }

            // Sort
            SectionLabel("Sort Notes")
            SettingsCard {
                listOf(
                    SettingsRepository.SORT_NEWEST to "Newest First",
                    SettingsRepository.SORT_OLDEST to "Oldest First",
                    SettingsRepository.SORT_TITLE  to "Title A–Z"
                ).forEachIndexed { i, (value, label) ->
                    if (i > 0) Divider()
                    Row(
                        modifier          = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = uiState.sortOrder == value,
                            onClick  = { viewModel.setSortOrder(value) },
                            colors   = RadioButtonDefaults.colors(selectedColor = Color(0xFF1A1A1A))
                        )
                        Text(label, fontSize = 14.sp, color = Color(0xFF1A1A1A))
                    }
                }
            }

            // About
            SectionLabel("About")
            SettingsCard {
                DeviceRow("App",     "Notes v2.0")
                Divider()
                DeviceRow("Course",  "PAM Week 8")
                Divider()
                DeviceRow("Storage", "Offline-first")
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
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
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
private fun Divider() {
    HorizontalDivider(
        modifier  = Modifier.padding(vertical = 4.dp),
        color     = Color(0xFFF0EFE9),
        thickness = 1.dp
    )
}

@Composable
private fun DeviceRow(label: String, value: String) {
    Row(
        modifier              = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 14.sp, color = Color(0xFF1A1A1A))
        Text(value, fontSize = 13.sp, color = Color(0xFF888885))
    }
}
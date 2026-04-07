package com.muhammadnurikhsan.myprofileapp.data

data class ProfileUiState(
    val name: String = "Muhammad Nurikhsan",
    val title: String = "Mahasiswa Teknik Informatika — ITERA",
    val bio: String = "Sedang belajar Kotlin Multiplatform dan Compose Multiplatform.",
    val email: String = "nurikhsan@student.itera.ac.id",
    val phone: String = "+62 812-3456-7890",
    val location: String = "Lampung Selatan, Indonesia",
    val isDarkMode: Boolean = false,
    val isEditMode: Boolean = false
)
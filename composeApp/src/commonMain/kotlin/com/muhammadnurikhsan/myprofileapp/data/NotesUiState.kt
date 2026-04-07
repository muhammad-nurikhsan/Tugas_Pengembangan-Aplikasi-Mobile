package com.muhammadnurikhsan.myprofileapp.data

data class NotesUiState(
    val notes: List<Note> = listOf(
        Note(1, "Belajar Kotlin", "Kotlin adalah bahasa modern untuk Android. Mendukung null-safety dan coroutines secara native.", false),
        Note(2, "Compose Multiplatform", "Memungkinkan satu codebase UI berjalan di Android dan iOS menggunakan Jetpack Compose.", true),
        Note(3, "Navigation Component", "NavHost, NavController, dan Routes adalah tiga komponen inti navigasi di Compose.", false),
        Note(4, "State Management & MVVM", "ViewModel dengan StateFlow adalah pola yang direkomendasikan untuk mengelola UI state.", true),
        Note(5, "Coroutines & Flow", "Coroutines memudahkan operasi asynchronous. Flow digunakan untuk data stream reaktif.", false)
    ),
    val isDarkMode: Boolean = false
)
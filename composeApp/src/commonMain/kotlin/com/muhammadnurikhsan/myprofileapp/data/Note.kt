package com.muhammadnurikhsan.myprofileapp.data

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false
)
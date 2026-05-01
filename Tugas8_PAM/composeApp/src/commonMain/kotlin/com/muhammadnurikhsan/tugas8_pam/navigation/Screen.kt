package com.muhammadnurikhsan.tugas8_pam.navigation

sealed class Screen(val route: String) {
    object NoteList  : Screen("note_list")
    object Favorites : Screen("favorites")
    object Profile   : Screen("profile")
    object Settings  : Screen("settings")

    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Long) = "note_detail/$noteId"
    }
    object AddNote : Screen("add_note")
    object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Long) = "edit_note/$noteId"
    }
}

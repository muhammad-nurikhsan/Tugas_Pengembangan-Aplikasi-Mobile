package com.muhammadnurikhsan.myprofileapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object NoteList  : Screen("note_list")
    object Favorites : Screen("favorites")
    object Profile   : Screen("profile")

    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }
    object AddNote : Screen("add_note")
    object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Int) = "edit_note/$noteId"
    }
}

sealed class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: String
) {
    object Notes     : BottomNavItem(Screen.NoteList,  Icons.Default.Home,     "Notes")
    object Favorites : BottomNavItem(Screen.Favorites, Icons.Default.Favorite, "Favorites")
    object Profile   : BottomNavItem(Screen.Profile,   Icons.Default.Person,   "Profile")
}
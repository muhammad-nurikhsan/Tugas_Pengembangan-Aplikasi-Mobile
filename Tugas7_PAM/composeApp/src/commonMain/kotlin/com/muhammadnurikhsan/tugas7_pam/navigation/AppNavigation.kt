package com.muhammadnurikhsan.tugas7_pam.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muhammadnurikhsan.tugas7_pam.data.NoteRepository
import com.muhammadnurikhsan.tugas7_pam.data.SettingsRepository
import com.muhammadnurikhsan.tugas7_pam.screens.*
import com.muhammadnurikhsan.tugas7_pam.viewmodel.NoteViewModel
import com.muhammadnurikhsan.tugas7_pam.viewmodel.SettingsViewModel

private val bottomNavRoutes = setOf(
    Screen.NoteList.route,
    Screen.Favorites.route,
    Screen.Profile.route,
    Screen.Settings.route
)

@Composable
fun AppNavigation(
    noteRepository: NoteRepository,
    settingsRepository: SettingsRepository
) {
    val noteViewModel     = remember { NoteViewModel(noteRepository) }
    val settingsViewModel = remember { SettingsViewModel(settingsRepository) }
    val navController     = rememberNavController()
    val currentRoute      = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavRoutes) {
                MinimalBottomNav(navController = navController)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        NavHost(
            navController    = navController,
            startDestination = Screen.NoteList.route,
            modifier         = Modifier.padding(paddingValues)
        ) {
            composable(Screen.NoteList.route) {
                NoteListScreen(
                    viewModel   = noteViewModel,
                    onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) },
                    onAddClick  = { navController.navigate(Screen.AddNote.route) }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    viewModel   = noteViewModel,
                    onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(settingsViewModel = settingsViewModel)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(viewModel = settingsViewModel)
            }
            composable(
                route     = Screen.NoteDetail.route,
                arguments = listOf(navArgument("noteId") { type = NavType.LongType })
            ) { back ->
                val noteId = back.arguments?.getLong("noteId") ?: return@composable
                NoteDetailScreen(
                    viewModel = noteViewModel,
                    noteId    = noteId,
                    onBack    = { navController.popBackStack() },
                    onEdit    = { navController.navigate(Screen.EditNote.createRoute(noteId)) }
                )
            }
            composable(Screen.AddNote.route) {
                AddNoteScreen(viewModel = noteViewModel, onBack = { navController.popBackStack() })
            }
            composable(
                route     = Screen.EditNote.route,
                arguments = listOf(navArgument("noteId") { type = NavType.LongType })
            ) { back ->
                val noteId = back.arguments?.getLong("noteId") ?: return@composable
                EditNoteScreen(viewModel = noteViewModel, noteId = noteId, onBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun MinimalBottomNav(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF111111),
        tonalElevation = 0.dp
    ) {
        listOf(
            Triple(Screen.NoteList.route,  Icons.Outlined.GridView,  "notes"),
            Triple(Screen.Favorites.route, Icons.Filled.Star,        "starred"),
            Triple(Screen.Profile.route,   Icons.Filled.Person,      "profile"),
            Triple(Screen.Settings.route,  Icons.Filled.Settings,    "settings"),
        ).forEach { (route, icon, label) ->
            val selected = currentRoute == route
            NavigationBarItem(
                selected = selected,
                onClick  = {
                    navController.navigate(route) {
                        popUpTo(Screen.NoteList.route) { saveState = true }
                        launchSingleTop = true
                        restoreState    = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(20.dp),
                        tint = if (selected) Color.White else Color(0xFF444444)
                    )
                },
                label = {
                    Text(
                        text     = label,
                        fontSize = 10.sp,
                        color    = if (selected) Color.White else Color(0xFF444444)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFF1E1E1E)
                )
            )
        }
    }
}
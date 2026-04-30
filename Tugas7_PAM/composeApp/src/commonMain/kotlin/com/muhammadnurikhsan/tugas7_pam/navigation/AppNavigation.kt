package com.muhammadnurikhsan.tugas7_pam.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

    val navController = rememberNavController()
    val currentRoute  = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavRoutes) {
                BottomNavigationBar(navController = navController)
            }
        }
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
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getLong("noteId") ?: return@composable
                NoteDetailScreen(
                    viewModel = noteViewModel,
                    noteId    = noteId,
                    onBack    = { navController.popBackStack() },
                    onEdit    = { navController.navigate(Screen.EditNote.createRoute(noteId)) }
                )
            }

            composable(Screen.AddNote.route) {
                AddNoteScreen(
                    viewModel = noteViewModel,
                    onBack    = { navController.popBackStack() }
                )
            }

            composable(
                route     = Screen.EditNote.route,
                arguments = listOf(navArgument("noteId") { type = NavType.LongType })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getLong("noteId") ?: return@composable
                EditNoteScreen(
                    viewModel = noteViewModel,
                    noteId    = noteId,
                    onBack    = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Screen.NoteList.route,
            onClick  = {
                navController.navigate(Screen.NoteList.route) {
                    popUpTo(Screen.NoteList.route) { saveState = true }
                    launchSingleTop = true; restoreState = true
                }
            },
            icon  = { Icon(Icons.Default.Home, null) },
            label = { Text("Notes") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Favorites.route,
            onClick  = {
                navController.navigate(Screen.Favorites.route) {
                    popUpTo(Screen.NoteList.route) { saveState = true }
                    launchSingleTop = true; restoreState = true
                }
            },
            icon  = { Icon(Icons.Default.Favorite, null) },
            label = { Text("Favorit") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Profile.route,
            onClick  = {
                navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.NoteList.route) { saveState = true }
                    launchSingleTop = true; restoreState = true
                }
            },
            icon  = { Icon(Icons.Default.Person, null) },
            label = { Text("Profil") }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Settings.route,
            onClick  = {
                navController.navigate(Screen.Settings.route) {
                    popUpTo(Screen.NoteList.route) { saveState = true }
                    launchSingleTop = true; restoreState = true
                }
            },
            icon  = { Icon(Icons.Default.Settings, null) },
            label = { Text("Settings") }
        )
    }
}

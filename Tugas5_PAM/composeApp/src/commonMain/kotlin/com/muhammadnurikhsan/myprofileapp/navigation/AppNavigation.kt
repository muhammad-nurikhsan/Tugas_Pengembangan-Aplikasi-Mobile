package com.muhammadnurikhsan.myprofileapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muhammadnurikhsan.myprofileapp.screens.*
import com.muhammadnurikhsan.myprofileapp.viewmodel.NotesViewModel

@Composable
fun AppNavigation(viewModel: NotesViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController    = navController,
            startDestination = Screen.NoteList.route,
            modifier         = Modifier.padding(paddingValues)
        ) {
            composable(Screen.NoteList.route) {
                NoteListScreen(
                    viewModel   = viewModel,
                    onNoteClick = { noteId -> navController.navigate(Screen.NoteDetail.createRoute(noteId)) },
                    onAddClick  = { navController.navigate(Screen.AddNote.route) }
                )
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    viewModel   = viewModel,
                    onNoteClick = { noteId -> navController.navigate(Screen.NoteDetail.createRoute(noteId)) }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(viewModel = viewModel)
            }

            composable(
                route     = Screen.NoteDetail.route,
                arguments = listOf(navArgument("noteId") { type = NavType.IntType })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                NoteDetailScreen(
                    viewModel = viewModel,
                    noteId    = noteId,
                    onBack    = { navController.popBackStack() },
                    onEdit    = { navController.navigate(Screen.EditNote.createRoute(noteId)) }
                )
            }

            composable(Screen.AddNote.route) {
                AddNoteScreen(
                    onBack = { navController.popBackStack() },
                    onSave = { title, content ->
                        viewModel.addNote(title, content)
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route     = Screen.EditNote.route,
                arguments = listOf(navArgument("noteId") { type = NavType.IntType })
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                EditNoteScreen(
                    viewModel = viewModel,
                    noteId    = noteId,
                    onBack    = { navController.popBackStack() },
                    onSave    = { id, title, content ->
                        viewModel.updateNote(id, title, content)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items        = listOf(BottomNavItem.Notes, BottomNavItem.Favorites, BottomNavItem.Profile)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick  = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState    = true
                    }
                },
                icon  = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
package com.muhammadnurikhsan.tugas8_pam.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muhammadnurikhsan.tugas8_pam.screens.*
import com.muhammadnurikhsan.tugas8_pam.viewmodel.NoteViewModel
import com.muhammadnurikhsan.tugas8_pam.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

private val bottomNavRoutes = setOf(
    Screen.NoteList.route,
    Screen.Favorites.route,
    Screen.Profile.route,
    Screen.Settings.route
)

data class NavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
)

@Composable
fun AppNavigation() {
    val noteViewModel: NoteViewModel         = koinViewModel()
    val settingsViewModel: SettingsViewModel = koinViewModel()
    val navController                        = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val navItems = listOf(
        NavItem(Screen.NoteList.route,  Icons.Filled.GridView,       Icons.Outlined.GridView,       "notes"),
        NavItem(Screen.Favorites.route, Icons.Filled.Favorite,       Icons.Outlined.FavoriteBorder, "starred"),
        NavItem(Screen.Profile.route,   Icons.Filled.Person,         Icons.Outlined.Person,         "profile"),
        NavItem(Screen.Settings.route,  Icons.Filled.Settings,       Icons.Outlined.Settings,       "settings"),
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavRoutes) {
                FloatingBottomNav(navController, navItems, currentRoute)
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
                FavoritesScreen(viewModel = noteViewModel,
                    onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) })
            }
            composable(Screen.Profile.route) {
                ProfileScreen(settingsViewModel = settingsViewModel)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(viewModel = settingsViewModel)
            }
            composable(Screen.NoteDetail.route,
                arguments = listOf(navArgument("noteId") { type = NavType.LongType })
            ) { back ->
                val noteId = back.arguments?.getLong("noteId") ?: return@composable
                NoteDetailScreen(viewModel = noteViewModel, noteId = noteId,
                    onBack = { navController.popBackStack() },
                    onEdit = { navController.navigate(Screen.EditNote.createRoute(noteId)) })
            }
            composable(Screen.AddNote.route) {
                AddNoteScreen(viewModel = noteViewModel, onBack = { navController.popBackStack() })
            }
            composable(Screen.EditNote.route,
                arguments = listOf(navArgument("noteId") { type = NavType.LongType })
            ) { back ->
                val noteId = back.arguments?.getLong("noteId") ?: return@composable
                EditNoteScreen(viewModel = noteViewModel, noteId = noteId,
                    onBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun FloatingBottomNav(navController: NavController, items: List<NavItem>, currentRoute: String?) {
    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp)) {
        Surface(
            modifier       = Modifier.fillMaxWidth().shadow(12.dp, RoundedCornerShape(28.dp)),
            shape          = RoundedCornerShape(28.dp),
            color          = Color.White,
            tonalElevation = 0.dp
        ) {
            Row(
                modifier              = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val selected = currentRoute == item.route
                    NavPill(item = item, selected = selected) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.NoteList.route) { saveState = true }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavPill(item: NavItem, selected: Boolean, onClick: () -> Unit) {
    val accent = Color(0xFF1A1A1A)
    if (selected) {
        Surface(onClick = onClick, shape = RoundedCornerShape(20.dp), color = Color(0xFFF0EFE9)) {
            Row(
                modifier              = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(item.selectedIcon, null, tint = accent, modifier = Modifier.size(18.dp))
                Text(item.label, color = accent, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    } else {
        IconButton(onClick = onClick) {
            Icon(item.unselectedIcon, null, tint = Color(0xFFBBBBB5), modifier = Modifier.size(22.dp))
        }
    }
}
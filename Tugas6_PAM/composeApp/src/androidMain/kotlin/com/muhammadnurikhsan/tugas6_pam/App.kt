package com.muhammadnurikhsan.tugas6_pam

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muhammadnurikhsan.tugas6_pam.data.repository.ArticleRepository
import com.muhammadnurikhsan.tugas6_pam.network.HttpClientFactory
import com.muhammadnurikhsan.tugas6_pam.ui.ArticlesViewModel
import com.muhammadnurikhsan.tugas6_pam.ui.screens.ArticleDetailScreen
import com.muhammadnurikhsan.tugas6_pam.ui.screens.ArticleListScreen

@Composable
fun App() {
    val navController = rememberNavController()
    val client = remember { HttpClientFactory.create() }
    val repository = remember { ArticleRepository(client) }
    val viewModel = remember { ArticlesViewModel(repository) }

    NavHost(
        navController = navController,
        startDestination = "articles"
    ) {
        composable("articles") {
            ArticleListScreen(
                viewModel = viewModel,
                onArticleClick = { id ->
                    navController.navigate("articles/$id")
                }
            )
        }
        composable("articles/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt()
                ?: return@composable
            ArticleDetailScreen(
                articleId = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
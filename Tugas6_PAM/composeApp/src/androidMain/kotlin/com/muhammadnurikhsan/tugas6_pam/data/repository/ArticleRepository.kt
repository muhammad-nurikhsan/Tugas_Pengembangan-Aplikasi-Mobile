package com.muhammadnurikhsan.tugas6_pam.data.repository

import com.muhammadnurikhsan.tugas6_pam.data.model.Article
import com.muhammadnurikhsan.tugas6_pam.data.model.ArticlesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ArticleRepository(private val client: HttpClient) {
    private val baseUrl = "https://dummyjson.com"

    suspend fun getArticles(): Result<List<Article>> {
        return try {
            val response: ArticlesResponse = client.get("$baseUrl/posts").body()
            Result.success(response.posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getArticleById(id: Int): Result<Article> {
        return try {
            val article: Article = client.get("$baseUrl/posts/$id").body()
            Result.success(article)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
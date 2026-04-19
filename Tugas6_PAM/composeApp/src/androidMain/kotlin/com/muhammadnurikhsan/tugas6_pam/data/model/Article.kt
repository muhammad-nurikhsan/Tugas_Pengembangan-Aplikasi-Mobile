package com.muhammadnurikhsan.tugas6_pam.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int,
    val title: String,
    val body: String,
    val tags: List<String> = emptyList(),
    val userId: Int
)

@Serializable
data class ArticlesResponse(
    val posts: List<Article>
)
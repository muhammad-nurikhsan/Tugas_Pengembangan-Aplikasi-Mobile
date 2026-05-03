package com.muhammadnurikhsan.tugas9_pam.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequest(
    val contents: List<GeminiContent>,
    @SerialName("generationConfig")
    val generationConfig: GenerationConfig? = null,
    @SerialName("systemInstruction")
    val systemInstruction: GeminiContent? = null
)

@Serializable
data class GeminiContent(
    val parts: List<GeminiPart>,
    val role: String = "user"
)

@Serializable
data class GeminiPart(
    val text: String
)

@Serializable
data class GenerationConfig(
    val temperature: Double = 0.7,
    @SerialName("maxOutputTokens")
    val maxOutputTokens: Int = 1000,
    @SerialName("topP")
    val topP: Double = 0.95
)

@Serializable
data class GeminiResponse(
    val candidates: List<GeminiCandidate>? = null,
    val error: GeminiError? = null
)

@Serializable
data class GeminiCandidate(
    val content: GeminiContent,
    @SerialName("finishReason")
    val finishReason: String? = null
)

@Serializable
data class GeminiError(
    val code: Int,
    val message: String,
    val status: String
)
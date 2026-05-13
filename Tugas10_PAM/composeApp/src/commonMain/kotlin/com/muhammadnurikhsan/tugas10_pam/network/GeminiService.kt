package com.muhammadnurikhsan.tugas10_pam.network

import com.muhammadnurikhsan.tugas10_pam.config.ApiConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

class GeminiService {

    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta"
    private val model = "gemini-2.5-flash"

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient         = true
            })
        }
    }

    private val SYSTEM_CHAT = GeminiContent(
        role  = "user",
        parts = listOf(GeminiPart(
            """
            Kamu adalah asisten catatan pribadi yang cerdas, ramah, dan ringkas.
            Tugasmu membantu pengguna mengelola, menganalisis, dan mendapatkan insight dari catatan mereka.
            
            Aturan:
            - Selalu balas dalam Bahasa Indonesia
            - Jawaban harus ringkas, jelas, dan langsung ke inti
            - Jika ditanya soal catatan, berikan analisis yang berguna
            - Jangan mengarang fakta; jika tidak tahu, katakan dengan jujur
            - Gunakan bahasa yang santai tapi tetap informatif
            """.trimIndent()
        ))
    )

    private val SYSTEM_SUMMARIZE = """
        Kamu adalah asisten ringkasan teks profesional.
        Tugasmu merangkum catatan menjadi 2-3 kalimat padat yang menangkap poin-poin utama.
        
        Aturan:
        - Jawaban HANYA berisi ringkasan, tanpa kalimat pembuka seperti "Berikut ringkasannya:"
        - Gunakan Bahasa Indonesia yang jelas dan natural
        - Maksimal 3 kalimat
        - Fokus pada informasi paling penting
    """.trimIndent()

    suspend fun summarizeNote(title: String, content: String): Result<String> {
        val prompt = "Judul: $title\n\nIsi:\n$content"
        return retryWithBackoff {
            generateContent(
                userPrompt       = prompt,
                systemPrompt     = SYSTEM_SUMMARIZE,
                multiTurnHistory = emptyList()
            )
        }
    }

    suspend fun chat(
        userMessage: String,
        history    : List<GeminiContent>
    ): Result<String> = retryWithBackoff {
        println("DEBUG: chat() called with message: $userMessage")
        val contents = mutableListOf<GeminiContent>()
        contents.add(SYSTEM_CHAT)
        contents.addAll(history)
        contents.add(GeminiContent(role = "user", parts = listOf(GeminiPart(userMessage))))

        val request = GeminiRequest(
            contents         = contents,
            generationConfig = GenerationConfig(temperature = 0.8, maxOutputTokens = 800)
        )
        callApi(request)
    }

    private suspend fun generateContent(
        userPrompt      : String,
        systemPrompt    : String,
        multiTurnHistory: List<GeminiContent>
    ): String {
        val contents = mutableListOf<GeminiContent>()
        contents.addAll(multiTurnHistory)
        contents.add(GeminiContent(role = "user", parts = listOf(GeminiPart(userPrompt))))

        val request = GeminiRequest(
            contents = contents,
            systemInstruction = GeminiContent(
                role  = "user",
                parts = listOf(GeminiPart(systemPrompt))
            ),
            generationConfig = GenerationConfig(temperature = 0.5, maxOutputTokens = 300)
        )
        return callApi(request)
    }

    private suspend fun callApi(request: GeminiRequest): String {
        println("DEBUG: Calling Gemini API with key: ${ApiConfig.geminiApiKey.take(10)}...")

        val response: GeminiResponse = client.post(
            "$baseUrl/models/$model:generateContent"
        ) {
            contentType(ContentType.Application.Json)
            parameter("key", ApiConfig.geminiApiKey)
            setBody(request)
        }.body()

        println("DEBUG: Response received, error=${response.error}, candidates=${response.candidates?.size}")

        if (response.error != null) {
            println("DEBUG: Error code=${response.error.code}, msg=${response.error.message}")
            throw when (response.error.code) {
                401, 403    -> AIError.Unauthorized(response.error.message)
                429         -> AIError.RateLimited()
                in 500..599 -> AIError.ServerError(response.error.message)
                else        -> AIError.ServerError(response.error.message)
            }
        }

        return response.candidates
            ?.firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
            ?: throw AIError.EmptyResponse()
    }

    private suspend fun <T> retryWithBackoff(
        times       : Int    = 3,
        initialDelay: Long   = 1_000,
        maxDelay    : Long   = 8_000,
        factor      : Double = 2.0,
        block       : suspend () -> T
    ): Result<T> {
        println("DEBUG: retryWithBackoff called")
        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                return Result.success(block())
            } catch (e: AIError.RateLimited) {
                delay(e.retryAfterSeconds * 1_000L)
            } catch (e: AIError.ServerError) {
                delay(currentDelay)
                currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
            } catch (e: Exception) {
                return Result.failure(mapException(e))
            }
        }
        return try {
            Result.success(block())
        } catch (e: Exception) {
            Result.failure(mapException(e))
        }
    }

    private fun mapException(e: Exception): AIError = when (e) {
        is AIError -> e
        else       -> AIError.NetworkError(e.message ?: "Unknown error")
    }
}

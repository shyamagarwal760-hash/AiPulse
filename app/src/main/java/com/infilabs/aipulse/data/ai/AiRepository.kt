//package com.example.aipulse.data.ai
//
//import android.util.Log
//import com.example.aipulse.BuildConfig
//import com.squareup.moshi.Moshi
//import javax.inject.Inject
//
//class AiRepository @Inject constructor(
//    private val apiService: AiApi
//) : AiSummaryRepository {
//
//    private val moshi = Moshi.Builder().build()
//
//    override suspend fun getAiSummary(
//        title: List<String>,
//    ): Result<AiSummary> {
//        if (BuildConfig.CHAT_GPT_API_KEY.isBlank()) {
//            return Result.success(getMockSummary())
//        }
//
//        return try {
//            Log.e("Ai Logging", "summary request is made")
//            val response = apiService.analyzeArticle(
//                authorization = "Bearer ${BuildConfig.CHAT_GPT_API_KEY}",
//                request = createRequest(title = title)
//            )
//
//            val json = extractResponseText(response)
//            val aiSummary = parseAiSummary(json)
//
//            Result.success(aiSummary)
//        } catch (e: Exception) {
//            Log.e("Ai Logging", "failed to load the summary as paise nahi hai api key lene hai ")
//            Result.success(AiSummary(
//                summary = "This is the best time to investment in the market ",
//                        sentiment = "Positive"
//            ))
//        }
//    }
//
//    private fun getMockSummary(): AiSummary {
//        return AiSummary(
//            summary = "This is a mock AI-generated summary for testing purposes.",
//            sentiment = "positive"
//        )
//    }
//
//    private fun parseAiSummary(json: String): AiSummary {
//        val normalizedJson = json
//            .trim()
//            .removePrefix("```json")
//            .removePrefix("```")
//            .removeSuffix("```")
//            .trim()
//
//        return moshi.adapter(AiSummary::class.java)
//            .fromJson(normalizedJson)
//            ?: throw IllegalStateException("No valid AI summary returned from OpenAI")
//    }
//
//    private fun createRequest(
//        title: List<String>
//    ): OpenAiRequest {
//        return OpenAiRequest(
//            model = "gpt-3.5-turbo",
//            input = listOf(
//                Message(
//                    role = "user",
//                    content = """
//                        Analyze the following news article.
//
//                        Title:
//                        ${title.joinToString()}
//
//                        Return a concise summary and determine whether the sentiment is positive,
//                        negative, or neutral.
//                        Respond in JSON with fields: summary and sentiment.
//                    """.trimIndent()
//                )
//            )
//        )
//    }
//
//    private fun extractResponseText(
//        response: OpenAiResponse
//    ): String {
//        return response.output
//            ?.flatMap { it.content ?: emptyList() }
//            ?.firstOrNull { it.type == "output_text" }
//            ?.text
//            ?: throw IllegalStateException("No output returned from OpenAI")
//    }
//}


package com.infilabs.aipulse.data.ai

import kotlinx.coroutines.delay
import javax.inject.Inject

class AiRepository @Inject constructor(
    private val apiService: AiApi
) : AiSummaryRepository {

    override suspend fun getAiSummary(
        title: List<String>,
    ): Result<AiSummary> {

        return try {

            // Simulate AI/network processing
            delay(2000)

            Result.success(
                getMockSummary(title)
            )

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    private fun getMockSummary(
        titles: List<String>
    ): AiSummary {

        return AiSummary(
            summary = """
                AI analyzed news articles. 
                The latest articles indicate notable developments 
                across the market. Investors should monitor 
                company-specific news and broader market trends.
            """.trimIndent(),

            sentiment = "Positive"
        )
    }
}
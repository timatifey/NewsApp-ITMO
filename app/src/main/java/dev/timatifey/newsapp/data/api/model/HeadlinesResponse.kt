package dev.timatifey.newsapp.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

/**
 * @author timatifey
 */
@Serializable
data class HeadlinesResponse(
    @SerialName("status")
    val status: Status,

    @SerialName("totalResults")
    val totalResults: Int,

    @SerialName("articles")
    val articles: List<ArticleDto>,

    @SerialName("code")
    val code: String? = null,

    @SerialName("message")
    val message: String? = null,
)

@Serializable
enum class Status {

    @SerialName("ok")
    OK,

    @SerialName("error")
    ERROR,
}

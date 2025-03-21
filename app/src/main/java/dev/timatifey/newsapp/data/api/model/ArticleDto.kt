package dev.timatifey.newsapp.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    @SerialName("source")
    val source: Source,

    @SerialName("author")
    val author: String?,

    @SerialName("title")
    val title: String?,

    @SerialName("description")
    val description: String?,

    @SerialName("url")
    val url: String?,

    @SerialName("urlToImage")
    val urlToImage: String?,

    @SerialName("publishedAt")
    val publishedAt: String?,

    @SerialName("content")
    val content: String?,
)

@Serializable
data class Source(
    @SerialName("id")
    val id: String? = null,

    @SerialName("name")
    val name: String? = null,
)
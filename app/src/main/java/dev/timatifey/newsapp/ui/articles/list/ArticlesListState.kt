package dev.timatifey.newsapp.ui.articles.list

/**
 * @author timatifey
 */
data class ArticlesListState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)

data class ArticleUi(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)

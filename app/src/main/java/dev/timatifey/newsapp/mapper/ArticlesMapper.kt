package dev.timatifey.newsapp.mapper

import dev.timatifey.newsapp.data.api.model.ArticleDto
import dev.timatifey.newsapp.data.database.model.ArticleEntity

/**
 * @author timatifey
 */
object ArticlesMapper {

    fun fromDtoToEntity(dto: ArticleDto, page: Int): ArticleEntity {
        return ArticleEntity(
            author = dto.author,
            title = dto.title,
            description = dto.description,
            url = dto.url,
            urlToImage = dto.urlToImage,
            publishedAt = dto.publishedAt,
            content = dto.content,
            page = page,
        )
    }
}
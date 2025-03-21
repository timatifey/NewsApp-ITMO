package dev.timatifey.newsapp.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

/**
 * @author timatifey
 */
@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Long = 0,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    @ColumnInfo("url_to_image")
    val urlToImage: String?,
    @ColumnInfo("published_at")
    val publishedAt: String?,
    val content: String?,
    val page: Int,
)

@Entity(tableName = "articles_fts")
@Fts4(contentEntity = ArticleEntity::class)
data class ArticleEntityFts(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val id: Long,
    val author: String?,
    val description: String?,
    val title: String?,
    val content: String?,
)


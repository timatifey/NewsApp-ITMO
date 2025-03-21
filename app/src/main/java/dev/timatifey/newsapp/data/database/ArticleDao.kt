package dev.timatifey.newsapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import dev.timatifey.newsapp.data.database.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author timatifey
 */
@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<ArticleEntity>)

    @Query("SELECT * FROM articles")
    suspend fun getOneOfAll(): List<ArticleEntity>

    @Query("SELECT * FROM articles")
    fun getAll(): PagingSource<Int, ArticleEntity>

    @Query(
        """
            SELECT articles.* 
                 FROM articles_fts 
                 JOIN articles ON (articles_fts.rowid = _id )
                 WHERE articles_fts MATCH :query
        """
    )
    fun pagingSource(query: String): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM articles WHERE title LIKE :query")
    suspend fun deleteByQuery(query: String)

    @Query("DELETE FROM articles")
    suspend fun clearAll()
}

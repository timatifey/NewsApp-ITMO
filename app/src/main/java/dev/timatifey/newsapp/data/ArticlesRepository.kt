package dev.timatifey.newsapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.timatifey.newsapp.data.api.NewsApiService
import dev.timatifey.newsapp.data.database.NewsAppDb
import dev.timatifey.newsapp.data.database.model.ArticleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author timatifey
 */
interface ArticlesRepository {

    fun getArticles(
        query: String,
        country: String,
        category: String,
    ): Flow<PagingData<ArticleEntity>>
}

@Singleton
@OptIn(ExperimentalPagingApi::class)
internal class ArticlesRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService,
    private val db: NewsAppDb,
) : ArticlesRepository {

    override fun getArticles(
        query: String,
        country: String,
        category: String,
    ): Flow<PagingData<ArticleEntity>> {
        val pageSize = 10
        val articlesDao = db.articleDao()
        val pager = Pager(
            config = PagingConfig(pageSize = pageSize),
            remoteMediator = ArticlesMediator(
                query = query,
                country = country,
                category = category,
                pageSize = pageSize,
                db = db,
                networkService = apiService,
            )
        ) {
            if (query.isEmpty()) {
                articlesDao.getAll()
            } else {
                articlesDao.pagingSource(query)
            }
        }
        return pager.flow
    }
}
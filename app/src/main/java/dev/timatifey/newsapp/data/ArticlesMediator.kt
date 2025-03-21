package dev.timatifey.newsapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.timatifey.newsapp.data.api.NewsApiService
import dev.timatifey.newsapp.data.api.model.Status
import dev.timatifey.newsapp.data.database.NewsAppDb
import dev.timatifey.newsapp.data.database.model.ArticleEntity
import dev.timatifey.newsapp.mapper.ArticlesMapper
import retrofit2.HttpException
import java.io.IOException

/**
 * @author timatifey
 */
@OptIn(ExperimentalPagingApi::class)
internal class ArticlesMediator constructor(
    private val query: String,
    private val country: String,
    private val category: String,
    private val pageSize: Int,
    private val db: NewsAppDb,
    private val networkService: NewsApiService,
) : RemoteMediator<Int, ArticleEntity>() {

    private val articlesDao = db.articleDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    lastItem.page
                }
            }
            val nextPage = if (loadKey == null) 1 else loadKey + 1
            val response = networkService.getTopHeadlines(
                country = country,
                category = category,
                query = query,
                pageSize = pageSize,
                page = nextPage,
            )

            if (response.status == Status.OK) {
                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        articlesDao.deleteByQuery(query)
                    }

                    articlesDao.insertAll(response.articles.map {
                        ArticlesMapper.fromDtoToEntity(
                            it,
                            page = nextPage
                        )
                    })
                }

                MediatorResult.Success(endOfPaginationReached = response.articles.isEmpty())
            } else {
                MediatorResult.Error(Throwable(response.message))
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}

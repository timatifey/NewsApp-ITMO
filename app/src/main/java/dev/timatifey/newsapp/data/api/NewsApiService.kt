package dev.timatifey.newsapp.data.api

import dev.timatifey.newsapp.data.api.model.HeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author timatifey
 */
interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = null,
        @Query("category") category: String? = null,
        @Query("query") query: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
    ): HeadlinesResponse
}

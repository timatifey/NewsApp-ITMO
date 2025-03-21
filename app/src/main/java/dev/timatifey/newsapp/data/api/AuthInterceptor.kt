package dev.timatifey.newsapp.data.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author timatifey
 */
internal class AuthInterceptor(private val apiToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request.newBuilder().addHeader("X-Api-Key", apiToken).build())
    }
}
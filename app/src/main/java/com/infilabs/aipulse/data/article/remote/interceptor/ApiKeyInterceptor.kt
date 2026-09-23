package com.infilabs.aipulse.data.article.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor(
    private val apikey : String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("X-Api-Key",apikey)
            .build()

        return chain.proceed(request)
    }
}

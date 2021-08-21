package com.mandiri.goldmarket.data.remote.interceptor

import com.mandiri.goldmarket.utils.CustomSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(private val sharedPreferences: CustomSharedPreferences): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originReq = chain.request()
        if (!originReq.url().toString().contains("auth")) {
            sharedPreferences.retrieveString(CustomSharedPreferences.Key.TOKEN)?.let {
                val requestBuilder = originReq.newBuilder()
                    .header("Authorization", "Bearer ${it}")
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
            return chain.proceed(originReq)
        }
        return chain.proceed(originReq)
    }
}
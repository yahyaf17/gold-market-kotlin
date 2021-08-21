package com.mandiri.goldmarket.data.remote

import com.mandiri.goldmarket.BuildConfig
import com.mandiri.goldmarket.data.remote.api.TransactionApi
import com.mandiri.goldmarket.data.remote.api.AuthApi
import com.mandiri.goldmarket.data.remote.api.CustomerApi
import com.mandiri.goldmarket.data.remote.api.HistoryApi
import com.mandiri.goldmarket.data.remote.api.PocketApi
import com.mandiri.goldmarket.data.remote.api.ProductApi
import com.mandiri.goldmarket.data.remote.interceptor.AuthTokenInterceptor
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitInstance(private val sharedPreferences: CustomSharedPreferences) {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthTokenInterceptor(sharedPreferences))
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    val customerApi: CustomerApi by lazy {
        retrofit.create(CustomerApi::class.java)
    }

    val productApi: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    val pocketApi: PocketApi by lazy {
        retrofit.create(PocketApi::class.java)
    }

    val transactionApi: TransactionApi by lazy {
        retrofit.create(TransactionApi::class.java)
    }

    val historyApi: HistoryApi by lazy {
        retrofit.create(HistoryApi::class.java)
    }

}
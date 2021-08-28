package com.mandiri.goldmarket.di.data

import com.mandiri.goldmarket.BuildConfig
import com.mandiri.goldmarket.data.remote.api.*
import com.mandiri.goldmarket.data.remote.interceptor.AuthTokenInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(authTokenInterceptor: AuthTokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authTokenInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideCustomerApi(retrofit: Retrofit): CustomerApi {
       return retrofit.create(CustomerApi::class.java)
    }

    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductApi {
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    fun providePocketApi(retrofit: Retrofit): PocketApi {
        return retrofit.create(PocketApi::class.java)
    }

    @Provides
    fun provideTransactionApi(retrofit: Retrofit): TransactionApi {
        return retrofit.create(TransactionApi::class.java)
    }

    @Provides
    fun provideHistoryApi(retrofit: Retrofit): HistoryApi {
        return retrofit.create(HistoryApi::class.java)
    }
}
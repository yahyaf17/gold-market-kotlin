package com.mandiri.goldmarket.di.data

import android.app.Application
import com.mandiri.goldmarket.data.db.AppDatabase
import com.mandiri.goldmarket.data.remote.api.*
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        DbModule::class,
        SharedPrefModule::class
    ]
)
interface DataComponent {
    fun provideAuthApi(): AuthApi
    fun provideCustomerApi(): CustomerApi
    fun provideHistoryApi(): HistoryApi
    fun providePocketApi(): PocketApi
    fun provideProductApi(): ProductApi
    fun provideTransactionApi(): TransactionApi
    fun provideDatabase(): AppDatabase
    fun provideSharedPref(): CustomSharedPreferences

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): DataComponent
    }
}
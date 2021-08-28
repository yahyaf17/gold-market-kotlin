package com.mandiri.goldmarket.di.feature.module

import com.mandiri.goldmarket.data.repository.customer.CustomerRepository
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRetrofit
import com.mandiri.goldmarket.data.repository.history.HistoryRepository
import com.mandiri.goldmarket.data.repository.history.HistoryRepositoryRetrofit
import com.mandiri.goldmarket.data.repository.pocket.PocketRepository
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryRetrofit
import com.mandiri.goldmarket.data.repository.product.ProductRepository
import com.mandiri.goldmarket.data.repository.product.ProductRetrofitRepository
import dagger.Binds
import dagger.Module

@Module
abstract class HistoryRepoModule {
    @Binds
    abstract fun bindsHistoryRepository(historyRepositoryImpl: HistoryRepositoryRetrofit):
            HistoryRepository
}
package com.mandiri.goldmarket.di.feature.module

import com.mandiri.goldmarket.data.repository.history.HistoryRepository
import com.mandiri.goldmarket.data.repository.history.HistoryRepositoryRetrofit
import dagger.Binds
import dagger.Module

@Module
abstract class HistoryRepoModule {
    @Binds
    abstract fun bindsHistoryRepository(historyRepositoryImpl: HistoryRepositoryRetrofit):
            HistoryRepository
}
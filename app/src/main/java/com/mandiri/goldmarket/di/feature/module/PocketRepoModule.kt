package com.mandiri.goldmarket.di.feature.module

import com.mandiri.goldmarket.data.repository.pocket.PocketRepository
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryRetrofit
import dagger.Binds
import dagger.Module

@Module
abstract class PocketRepoModule {
    @Binds
    abstract fun bindsPocketRepository(customerRepositoryImpl: PocketRepositoryRetrofit):
            PocketRepository
}
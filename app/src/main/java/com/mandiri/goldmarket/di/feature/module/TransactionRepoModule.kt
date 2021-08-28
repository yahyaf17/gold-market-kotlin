package com.mandiri.goldmarket.di.feature.module

import com.mandiri.goldmarket.data.repository.transaction.TransactionRepository
import com.mandiri.goldmarket.data.repository.transaction.TransactionRepositoryRetrofit
import dagger.Binds
import dagger.Module

@Module
abstract class TransactionRepoModule {
    @Binds
    abstract fun bindsTransactionRepository(transactionRepositoryImpl: TransactionRepositoryRetrofit):
            TransactionRepository
}
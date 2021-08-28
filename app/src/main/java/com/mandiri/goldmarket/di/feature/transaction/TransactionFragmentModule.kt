package com.mandiri.goldmarket.di.feature.transaction

import com.mandiri.goldmarket.data.repository.pocket.PocketRepository
import com.mandiri.goldmarket.data.repository.transaction.TransactionRepository
import com.mandiri.goldmarket.presentation.maintab.transaction.TransactionFragment
import com.mandiri.goldmarket.presentation.maintab.transaction.TransactionViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class TransactionFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeTransactionFragment(): TransactionFragment

    companion object {
        @Provides
        fun provideTransactionViewModel(
            transactionRepository: TransactionRepository,
            pocketRepository: PocketRepository
        ): TransactionViewModel {
            return TransactionViewModel(pocketRepository, transactionRepository)
        }
    }
}
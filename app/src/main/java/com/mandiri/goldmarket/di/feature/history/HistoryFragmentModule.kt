package com.mandiri.goldmarket.di.feature.history

import com.mandiri.goldmarket.data.repository.customer.CustomerRepository
import com.mandiri.goldmarket.data.repository.history.HistoryRepository
import com.mandiri.goldmarket.data.repository.pocket.PocketRepository
import com.mandiri.goldmarket.data.repository.product.ProductRepository
import com.mandiri.goldmarket.presentation.maintab.history.HistoryFragment
import com.mandiri.goldmarket.presentation.maintab.history.HistoryViewModel
import com.mandiri.goldmarket.presentation.maintab.home.HomeFragment
import com.mandiri.goldmarket.presentation.maintab.home.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class HistoryFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeHistoryFragment(): HistoryFragment

    companion object {
        @Provides
        fun provideHistoryViewModel(
            historyRepository: HistoryRepository
        ): HistoryViewModel {
            return HistoryViewModel(historyRepository)
        }
    }
}
package com.mandiri.goldmarket.di.feature.home

import com.mandiri.goldmarket.data.repository.customer.CustomerRepository
import com.mandiri.goldmarket.data.repository.pocket.PocketRepository
import com.mandiri.goldmarket.data.repository.product.ProductRepository
import com.mandiri.goldmarket.presentation.maintab.home.HomeFragment
import com.mandiri.goldmarket.presentation.maintab.home.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    companion object {
        @Provides
        fun provideHomeViewModel(
            customerRepository: CustomerRepository,
            productRepository: ProductRepository,
            pocketRepository: PocketRepository
        ): HomeViewModel {
            return HomeViewModel(customerRepository, productRepository, pocketRepository)
        }
    }
}
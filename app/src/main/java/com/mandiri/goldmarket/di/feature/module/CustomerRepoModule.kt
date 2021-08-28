package com.mandiri.goldmarket.di.feature.module

import com.mandiri.goldmarket.data.repository.customer.CustomerRepository
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRetrofit
import dagger.Binds
import dagger.Module

@Module
abstract class CustomerRepoModule {
    @Binds
    abstract fun bindsCustomerRepository(customerRepositoryImpl: CustomerRepositoryRetrofit):
            CustomerRepository
}
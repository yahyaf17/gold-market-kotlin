package com.mandiri.goldmarket.di.feature.home

import com.mandiri.goldmarket.data.repository.customer.CustomerRepository
import com.mandiri.goldmarket.data.repository.pocket.PocketRepository
import com.mandiri.goldmarket.data.repository.product.ProductRepository
import com.mandiri.goldmarket.di.annotation.HomeScope
import com.mandiri.goldmarket.di.data.DataComponent
import com.mandiri.goldmarket.di.feature.module.CustomerRepoModule
import com.mandiri.goldmarket.di.feature.module.PocketRepoModule
import com.mandiri.goldmarket.di.feature.module.ProductRepoModule
import dagger.Component

@HomeScope
@Component(modules = [
    CustomerRepoModule::class,
    PocketRepoModule::class,
    ProductRepoModule::class], dependencies = [DataComponent::class])
interface HomeComponent {
    fun provideCustomerRepo(): CustomerRepository
    fun providePocketRepo(): PocketRepository
    fun provideProductRepo(): ProductRepository
}
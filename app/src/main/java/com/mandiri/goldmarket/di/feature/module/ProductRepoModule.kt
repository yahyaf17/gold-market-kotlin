package com.mandiri.goldmarket.di.feature.module

import com.mandiri.goldmarket.data.repository.product.ProductRepository
import com.mandiri.goldmarket.data.repository.product.ProductRetrofitRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ProductRepoModule {
    @Binds
    abstract fun bindsProductRepository(productRepositoryImpl: ProductRetrofitRepository):
            ProductRepository
}
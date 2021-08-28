package com.mandiri.goldmarket.di.feature.transaction

import com.mandiri.goldmarket.data.repository.transaction.TransactionRepository
import com.mandiri.goldmarket.di.annotation.TransactionScope
import com.mandiri.goldmarket.di.data.DataComponent
import com.mandiri.goldmarket.di.feature.module.PocketRepoModule
import com.mandiri.goldmarket.di.feature.module.TransactionRepoModule
import dagger.Component

@TransactionScope
@Component(modules = [
    TransactionRepoModule::class,
    PocketRepoModule::class], dependencies = [DataComponent::class])
interface TransactionComponent {
    fun provideTransactionRepo(): TransactionRepository
}
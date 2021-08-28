package com.mandiri.goldmarket.di.feature.history

import com.mandiri.goldmarket.data.repository.history.HistoryRepository
import com.mandiri.goldmarket.di.annotation.HistoryScope
import com.mandiri.goldmarket.di.data.DataComponent
import com.mandiri.goldmarket.di.feature.module.HistoryRepoModule
import dagger.Component

@HistoryScope
@Component(modules = [HistoryRepoModule::class], dependencies = [DataComponent::class])
interface HistoryComponent {
    fun provideHistoryRepo(): HistoryRepository
}
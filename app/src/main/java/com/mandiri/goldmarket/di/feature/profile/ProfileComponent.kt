package com.mandiri.goldmarket.di.feature.profile

import com.mandiri.goldmarket.data.repository.customer.CustomerRepository
import com.mandiri.goldmarket.data.repository.pocket.PocketRepository
import com.mandiri.goldmarket.di.annotation.ProfileScope
import com.mandiri.goldmarket.di.data.DataComponent
import com.mandiri.goldmarket.di.feature.module.CustomerRepoModule
import com.mandiri.goldmarket.di.feature.module.PocketRepoModule
import dagger.Component

@ProfileScope
@Component(modules = [CustomerRepoModule::class, PocketRepoModule::class],
    dependencies = [DataComponent::class])
interface ProfileComponent {
}
package com.mandiri.goldmarket.di.feature.auth

import com.mandiri.goldmarket.data.repository.auth.AuthRepository
import com.mandiri.goldmarket.di.annotation.AuthScope
import com.mandiri.goldmarket.di.data.DataComponent
import dagger.Component

@AuthScope
@Component(modules = [AuthRepoModule::class], dependencies = [DataComponent::class])
interface AuthComponent {
    fun provideRegisterRepo(): AuthRepository
}
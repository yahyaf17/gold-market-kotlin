package com.mandiri.goldmarket.di.feature.auth

import com.mandiri.goldmarket.data.repository.auth.AuthRepository
import com.mandiri.goldmarket.data.repository.auth.AuthRetrofitRepository
import dagger.Binds
import dagger.Module

@Module
abstract class AuthRepoModule {
    @Binds
    abstract fun bindsAuthRepository(authenticationRepositoryImpl: AuthRetrofitRepository):
            AuthRepository
}
package com.mandiri.goldmarket.di.feature.auth.register

import com.mandiri.goldmarket.data.repository.auth.AuthRepository
import com.mandiri.goldmarket.presentation.onboarding.register.RegisterFragment
import com.mandiri.goldmarket.presentation.onboarding.register.RegisterViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class RegisterFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeRegisterFragment(): RegisterFragment

    companion object {
        @Provides
        fun provideRegisterViewModel(
            authRepository: AuthRepository
        ): RegisterViewModel {
            return RegisterViewModel(authRepository)
        }
    }
}
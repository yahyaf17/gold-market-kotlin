package com.mandiri.goldmarket.di.feature.auth.login

import com.mandiri.goldmarket.data.repository.auth.AuthRepository
import com.mandiri.goldmarket.presentation.onboarding.login.LoginFragment
import com.mandiri.goldmarket.presentation.onboarding.login.LoginViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    companion object {
        @Provides
        fun provideLoginViewModel(
            authRepository: AuthRepository
        ): LoginViewModel {
            return LoginViewModel(authRepository)
        }
    }
}
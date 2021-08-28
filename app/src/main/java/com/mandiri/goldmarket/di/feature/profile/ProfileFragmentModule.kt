package com.mandiri.goldmarket.di.feature.profile

import android.content.SharedPreferences
import com.mandiri.goldmarket.data.repository.customer.CustomerRepository
import com.mandiri.goldmarket.data.repository.pocket.PocketRepository
import com.mandiri.goldmarket.presentation.maintab.profile.ProfileFragment
import com.mandiri.goldmarket.presentation.maintab.profile.ProfileViewModel
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    companion object {
        @Provides
        fun provideHistoryViewModel(
            customerRepository: CustomerRepository,
            pocketRepository: PocketRepository
        ): ProfileViewModel {
            return ProfileViewModel(customerRepository, pocketRepository)
        }

        @Provides
        fun provideSharedPref(
            sharedPreferences: SharedPreferences
        ): CustomSharedPreferences {
            return CustomSharedPreferences(sharedPreferences)
        }
    }
}
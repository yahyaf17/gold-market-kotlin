package com.mandiri.goldmarket.di.app

import com.mandiri.goldmarket.di.feature.auth.login.LoginFragmentModule
import com.mandiri.goldmarket.di.feature.auth.register.RegisterFragmentModule
import com.mandiri.goldmarket.di.feature.history.HistoryFragmentModule
import com.mandiri.goldmarket.di.feature.home.HomeFragmentModule
import com.mandiri.goldmarket.di.feature.profile.ProfileFragmentModule
import com.mandiri.goldmarket.di.feature.transaction.TransactionFragmentModule
import dagger.Module

@Module(includes = [
    LoginFragmentModule::class,
    RegisterFragmentModule::class,
    HomeFragmentModule::class,
    HistoryFragmentModule::class,
    ProfileFragmentModule::class,
    TransactionFragmentModule::class
])
class FeatureModule {
}
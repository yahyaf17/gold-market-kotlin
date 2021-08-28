package com.mandiri.goldmarket.di.app

import com.mandiri.goldmarket.BaseApplication
import com.mandiri.goldmarket.di.annotation.AppScope
import com.mandiri.goldmarket.di.feature.auth.AuthComponent
import com.mandiri.goldmarket.di.feature.history.HistoryComponent
import com.mandiri.goldmarket.di.feature.home.HomeComponent
import com.mandiri.goldmarket.di.feature.profile.ProfileComponent
import com.mandiri.goldmarket.di.feature.transaction.TransactionComponent
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class],
    dependencies = [
        AuthComponent::class,
        HomeComponent::class,
        HistoryComponent::class,
        ProfileComponent::class,
        TransactionComponent::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

}
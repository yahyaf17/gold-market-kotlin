package com.mandiri.goldmarket

import com.mandiri.goldmarket.di.app.DaggerAppComponent
import com.mandiri.goldmarket.di.data.DaggerDataComponent
import com.mandiri.goldmarket.di.feature.auth.AuthComponent
import com.mandiri.goldmarket.di.data.DataComponent
import com.mandiri.goldmarket.di.feature.auth.DaggerAuthComponent
import com.mandiri.goldmarket.di.feature.history.DaggerHistoryComponent
import com.mandiri.goldmarket.di.feature.history.HistoryComponent
import com.mandiri.goldmarket.di.feature.home.DaggerHomeComponent
import com.mandiri.goldmarket.di.feature.home.HomeComponent
import com.mandiri.goldmarket.di.feature.profile.DaggerProfileComponent
import com.mandiri.goldmarket.di.feature.profile.ProfileComponent
import com.mandiri.goldmarket.di.feature.transaction.DaggerTransactionComponent
import com.mandiri.goldmarket.di.feature.transaction.TransactionComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BaseApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .authComponent(provideRegisterComponent())
            .historyComponent(provideHistoryComponent())
            .homeComponent(provideHomeComponent())
            .profileComponent(provideProfileComponent())
            .transactionComponent(provideTransactionComponent())
            .build()
    }

    private fun provideDataComponent(): DataComponent {
        return DaggerDataComponent.builder().application(this).build()
    }

    private fun provideRegisterComponent(): AuthComponent {
        return DaggerAuthComponent.builder().dataComponent(provideDataComponent()).build()
    }

    private fun provideHomeComponent(): HomeComponent {
        return DaggerHomeComponent.builder().dataComponent(provideDataComponent()).build()
    }

    private fun provideHistoryComponent(): HistoryComponent {
        return DaggerHistoryComponent.builder().dataComponent(provideDataComponent()).build()
    }

    private fun provideProfileComponent(): ProfileComponent {
        return DaggerProfileComponent.builder().dataComponent(provideDataComponent()).build()
    }

    private fun provideTransactionComponent(): TransactionComponent {
        return DaggerTransactionComponent.builder().dataComponent(provideDataComponent()).build()
    }

}
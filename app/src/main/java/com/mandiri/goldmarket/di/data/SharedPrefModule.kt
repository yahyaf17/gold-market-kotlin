package com.mandiri.goldmarket.di.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefModule {
    @Singleton
    @Provides
    fun provideSharedPref(application: Application): CustomSharedPreferences {
        val pref = application.applicationContext
            .getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        return CustomSharedPreferences(pref)
    }

}
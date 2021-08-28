package com.mandiri.goldmarket.di.data

import android.app.Application
import androidx.room.Room
import com.mandiri.goldmarket.data.db.AppDatabase
import com.mandiri.goldmarket.data.db.CustomerDao
import com.mandiri.goldmarket.data.db.HistoryDao
import com.mandiri.goldmarket.data.db.PocketDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "gold_market_db"
        ).build()
    }

    @Provides
    fun provideCustomerDao(db: AppDatabase): CustomerDao {
        return db.customerDao()
    }

    @Provides
    fun providePocketDao(db: AppDatabase): PocketDao {
        return db.pocketDao()
    }

    @Provides
    fun provideHistoryDao(db: AppDatabase): HistoryDao {
        return db.historyDao()
    }
}
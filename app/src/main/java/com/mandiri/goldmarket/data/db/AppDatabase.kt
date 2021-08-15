package com.mandiri.goldmarket.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.model.History
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.data.model.Product
import com.mandiri.goldmarket.utils.ConverterType

@Database(entities = [
    Customer::class,
    Pocket::class,
    Product::class,
    History::class], version = 3)
@TypeConverters(ConverterType::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun customerDao(): CustomerDao
    abstract fun pocketDao(): PocketDao
    abstract fun historyDao(): HistoryDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gold_market_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
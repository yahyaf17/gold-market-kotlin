package com.mandiri.goldmarket.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    val historyId: Int = 0,
    val product: String,
    @ColumnInfo(name = "purchase_type") val purchaseType: String,
    val amount: Double,
    @ColumnInfo(name = "total_price") val totalPrice: Double,
    @ColumnInfo(name = "pocket_name")val pocketName: String,
    @ColumnInfo(name = "purchase_date") val purchaseDate: Date,
    @ColumnInfo(name = "customer_owner_id") val customerId: Int
)
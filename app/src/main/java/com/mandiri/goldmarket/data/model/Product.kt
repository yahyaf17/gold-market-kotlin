package com.mandiri.goldmarket.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.math.BigDecimal

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    val productId: Int = 0,
    val name: String,
    @ColumnInfo(name = "price_buy") val priceBuy: Double,
    @ColumnInfo(name = "price_sell")val priceSell: Double
    )

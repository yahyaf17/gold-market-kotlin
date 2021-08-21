package com.mandiri.goldmarket.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "pocket")
data class Pocket(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pocket_id")
    val pocketId: Int = 0,
    var name: String,
    var amount: Double,
    val product: String,
    @ColumnInfo(name = "total_price") var totalPrice: Double,
    @ColumnInfo(name = "customer_owner_id") val pocketOwnerId: Int,
    @ColumnInfo(name = "product_pocket_id") var pocketProduct: Int
    )

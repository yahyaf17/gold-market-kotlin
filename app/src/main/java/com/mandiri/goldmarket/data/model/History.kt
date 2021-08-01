package com.mandiri.goldmarket.data.model

import java.math.BigDecimal
import java.util.*

data class History(
    val product: String,
    val purchaseType: String,
    val amount: Double,
    val totalPrice: BigDecimal,
    val pocketName: String,
    val purchaseDate: Date
)
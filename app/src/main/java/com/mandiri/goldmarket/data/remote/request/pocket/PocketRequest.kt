package com.mandiri.goldmarket.data.remote.request.pocket

data class PocketRequest(
    val customer: Customer,
    val pocketName: String,
    val pocketQty: Double,
    val product: Product,
    val totalAmount: Int
)
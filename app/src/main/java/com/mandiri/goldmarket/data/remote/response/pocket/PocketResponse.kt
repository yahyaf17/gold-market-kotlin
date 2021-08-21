package com.mandiri.goldmarket.data.remote.response.pocket

import com.mandiri.goldmarket.data.remote.request.pocket.Customer
import com.mandiri.goldmarket.data.remote.request.pocket.Product

data class PocketResponse(
    val customer: Customer,
    val id: String,
    val pocketName: String,
    val pocketQty: Double,
    val product: Product,
    val totalAmount: Int
)
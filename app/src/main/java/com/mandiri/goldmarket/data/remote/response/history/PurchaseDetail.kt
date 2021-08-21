package com.mandiri.goldmarket.data.remote.response.history

import com.mandiri.goldmarket.data.remote.request.pocket.Product

data class PurchaseDetail(
    val id: String,
    val price: Int,
    val product: Product,
    val quantityInGram: Double
)
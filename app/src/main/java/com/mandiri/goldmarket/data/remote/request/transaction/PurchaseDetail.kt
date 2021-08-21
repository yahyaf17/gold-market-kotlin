package com.mandiri.goldmarket.data.remote.request.transaction

import com.mandiri.goldmarket.data.remote.request.pocket.Product

data class PurchaseDetail(
    val price: Int,
    val product: Product,
    val quantityInGram: Double
)
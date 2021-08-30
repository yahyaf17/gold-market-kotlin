package com.mandiri.goldmarket.data.remote.request.pocket

data class EditPocketRequest(
    val id: String,
    val pocketName: String,
    val pocketQty: Double,
    val totalAmount: Int,
    val customer: Customer,
    val product: Product,

)
package com.mandiri.goldmarket.data.remote.response.history

import com.mandiri.goldmarket.data.remote.request.pocket.Customer

data class Content(
    val customer: Customer,
    val id: String,
    val purchaseDetailList: List<PurchaseDetail>,
    val purchaseType: Int,
    val transactionDate: String
)
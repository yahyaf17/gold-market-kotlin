package com.mandiri.goldmarket.data.remote.request.transaction

import com.mandiri.goldmarket.data.remote.request.pocket.Customer

data class Purchase(
    val customer: Customer,
    val purchaseDetailList: List<PurchaseDetail>,
    val purchaseType: Int,
    val transactionDate: String
)
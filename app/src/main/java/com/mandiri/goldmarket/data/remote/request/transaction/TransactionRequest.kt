package com.mandiri.goldmarket.data.remote.request.transaction

data class TransactionRequest(
    val idPocket: String,
    val purchase: Purchase
)
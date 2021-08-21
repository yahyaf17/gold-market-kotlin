package com.mandiri.goldmarket.data.remote.response.transaction

data class TransactionResponse(
    val error: String,
    val message: String,
    val path: String,
    val status: Int,
    val timestamp: String
)
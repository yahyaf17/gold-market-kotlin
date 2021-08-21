package com.mandiri.goldmarket.data.repository.transaction

import com.mandiri.goldmarket.data.remote.request.transaction.TransactionRequest
import com.mandiri.goldmarket.data.remote.response.transaction.TransactionResponse

interface TransactionRepository {
    suspend fun performTransaction(request: TransactionRequest): TransactionResponse?
}
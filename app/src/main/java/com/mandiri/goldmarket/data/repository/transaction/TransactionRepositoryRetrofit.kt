package com.mandiri.goldmarket.data.repository.transaction

import android.util.Log
import com.mandiri.goldmarket.data.remote.api.TransactionApi
import com.mandiri.goldmarket.data.remote.request.transaction.TransactionRequest
import com.mandiri.goldmarket.data.remote.response.transaction.TransactionResponse
import kotlinx.coroutines.withTimeout
import java.lang.Exception

class TransactionRepositoryRetrofit(private val transactionApi: TransactionApi): TransactionRepository {

    override suspend fun performTransaction(request: TransactionRequest): TransactionResponse? {
        return try {
            withTimeout(7000) {
                val response = transactionApi.performTransaction(request)
                if (response.isSuccessful) {
                    Log.d("TransactionRepo", "TransactionSuccess: ${response.body()}")
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("TransactionRepo", "TransactionFailed: ${e.localizedMessage}")
            null
        }
    }

}
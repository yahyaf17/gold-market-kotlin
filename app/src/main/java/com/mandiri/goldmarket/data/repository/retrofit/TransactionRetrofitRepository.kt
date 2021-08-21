package com.mandiri.goldmarket.data.repository.retrofit

import android.util.Log
import com.mandiri.goldmarket.data.remote.api.transaction.TransactionApi
import com.mandiri.goldmarket.data.remote.request.transaction.TransactionRequest
import com.mandiri.goldmarket.data.remote.response.transaction.TransactionResponse
import kotlinx.coroutines.withTimeout
import java.lang.Exception

class TransactionRetrofitRepository(private val transactionApi: TransactionApi) {

    suspend fun performTransaction(request: TransactionRequest): TransactionResponse? {
        return try {
//            withTimeout(200000000) {
                val response = transactionApi.performTransaction(request)
                if (response.isSuccessful) {
                    Log.d("TransactionRepo", "TransactionSuccess: ${response.body()}")
                    response.body()
                } else {
                    null
                }
//            }
        } catch (e: Exception) {
            Log.d("TransactionRepo", "TransactionFailed: ${e.localizedMessage}")
            null
        }
    }

}
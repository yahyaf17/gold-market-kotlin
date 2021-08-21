package com.mandiri.goldmarket.data.remote.api.transaction

import com.mandiri.goldmarket.data.remote.request.transaction.TransactionRequest
import com.mandiri.goldmarket.data.remote.response.transaction.TransactionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TransactionApi {

    @POST("purchases")
    suspend fun performTransaction(@Body request: TransactionRequest): Response<TransactionResponse>

}
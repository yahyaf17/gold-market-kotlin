package com.mandiri.goldmarket.data.remote.api

import com.mandiri.goldmarket.data.remote.response.history.HistoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryApi {

    @GET("purchases/customer/{customerId}?size=50")
    suspend fun getPurchaseHistoryByCustomer(@Path("customerId") customerId: String):
            Response<HistoriesResponse>

}
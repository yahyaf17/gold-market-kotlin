package com.mandiri.goldmarket.data.remote.api.pocket

import com.mandiri.goldmarket.data.remote.request.pocket.PocketRequest
import com.mandiri.goldmarket.data.remote.response.pocket.PocketResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PocketApi {

    @POST("pockets")
    suspend fun createPocket(@Body pocketRequest: PocketRequest): Response<PocketResponse>

    @GET("pockets/{id}")
    suspend fun findPocketById(@Path("id")id: String): Response<PocketResponse>

    @GET("customer/{id}/pockets")
    suspend fun getAllCustomerPockets(@Path("id") id: String): Response<List<PocketResponse>>

}
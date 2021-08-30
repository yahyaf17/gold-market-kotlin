package com.mandiri.goldmarket.data.remote.api

import com.mandiri.goldmarket.data.remote.request.pocket.EditPocketRequest
import com.mandiri.goldmarket.data.remote.request.pocket.PocketRequest
import com.mandiri.goldmarket.data.remote.response.pocket.PocketResponse
import retrofit2.Response
import retrofit2.http.*

interface PocketApi {

    @POST("pockets")
    suspend fun createPocket(@Body pocketRequest: PocketRequest): Response<PocketResponse>

    @GET("pockets/{id}")
    suspend fun findPocketById(@Path("id")id: String): Response<PocketResponse>

    @GET("customer/{id}/pockets")
    suspend fun getAllCustomerPockets(@Path("id") id: String): Response<List<PocketResponse>>

    @PUT("pockets")
    suspend fun editPocketName(@Body pocketRequest: EditPocketRequest): Response<PocketResponse>

    @DELETE("pockets")
    suspend fun deletePocket(@Query("id") id: String): Response<Int>
}
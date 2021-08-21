package com.mandiri.goldmarket.data.remote.api.product

import com.mandiri.goldmarket.data.remote.response.product.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<ProductResponse>

}
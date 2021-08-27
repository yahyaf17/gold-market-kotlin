package com.mandiri.goldmarket.data.remote.api

import com.mandiri.goldmarket.data.remote.response.product.AllProductResponse
import com.mandiri.goldmarket.data.remote.response.product.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<ProductResponse>

    @GET("products")
    suspend fun getAllProducts(): Response<List<ProductResponse>>

}
package com.mandiri.goldmarket.data.remote.api

import com.mandiri.goldmarket.data.remote.request.auth.RegisterRequest
import com.mandiri.goldmarket.data.remote.request.customer.CustomerRequest
import com.mandiri.goldmarket.data.remote.response.customer.CustomerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerApi {

    @GET(value = "customers/{customerId}")
    suspend fun getCustomerById(@Path("customerId") customerId: String): Response<CustomerResponse>

    @PUT(value = "customers/update")
    suspend fun updateCustomerData(@Body request: CustomerRequest): Response<CustomerResponse>

}
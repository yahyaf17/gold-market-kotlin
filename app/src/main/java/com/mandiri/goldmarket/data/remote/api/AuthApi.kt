package com.mandiri.goldmarket.data.remote.api

import com.mandiri.goldmarket.data.remote.request.auth.LoginRequest
import com.mandiri.goldmarket.data.remote.request.auth.RegisterRequest
import com.mandiri.goldmarket.data.remote.response.auth.LoginResponse
import com.mandiri.goldmarket.data.remote.response.auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/signup")
    suspend fun registerCustomer(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("auth/signin")
    suspend fun loginCustomer(@Body request: LoginRequest): Response<LoginResponse>

}
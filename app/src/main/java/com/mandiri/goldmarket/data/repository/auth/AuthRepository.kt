package com.mandiri.goldmarket.data.repository.auth

import com.mandiri.goldmarket.data.remote.request.auth.LoginRequest
import com.mandiri.goldmarket.data.remote.request.auth.RegisterRequest
import com.mandiri.goldmarket.data.remote.response.auth.LoginResponse
import com.mandiri.goldmarket.data.remote.response.auth.RegisterResponse

interface AuthRepository {
    suspend fun register(request: RegisterRequest): RegisterResponse?
    suspend fun login(request: LoginRequest): LoginResponse?
}
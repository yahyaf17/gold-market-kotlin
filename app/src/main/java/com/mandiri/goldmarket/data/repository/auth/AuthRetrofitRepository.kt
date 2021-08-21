package com.mandiri.goldmarket.data.repository.auth

import android.util.Log
import com.mandiri.goldmarket.data.remote.api.AuthApi
import com.mandiri.goldmarket.data.remote.request.auth.LoginRequest
import com.mandiri.goldmarket.data.remote.request.auth.RegisterRequest
import com.mandiri.goldmarket.data.remote.response.auth.LoginResponse
import com.mandiri.goldmarket.data.remote.response.auth.RegisterResponse
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import kotlinx.coroutines.withTimeout
import java.lang.Exception

class AuthRetrofitRepository(private val authApi: AuthApi,
                             private val sharedPreferences: CustomSharedPreferences
): AuthRepository {

    override suspend fun register(request: RegisterRequest): RegisterResponse? {
        return try {
            withTimeout(7000) {
                val response = authApi.registerCustomer(request)
                if (response.isSuccessful) {
                    Log.d("RegisterRepo", "registerSuccess: ")
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("RegisterRepo", "registerError: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun login(request: LoginRequest): LoginResponse? {
        return try {
            withTimeout(7000) {
                val response = authApi.loginCustomer(request)
                if (response.isSuccessful) {
                    Log.d("AuthRepo", "loginSuccess: ")
                    sharedPreferences.setValue(CustomSharedPreferences.Key.TOKEN, response.body()!!.token)
                    sharedPreferences.setValue(CustomSharedPreferences.Key.CUSTOMER_ID, response.body()!!.id)
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("AuthRepo", "loginFailed: ${e.localizedMessage}")
            null
        }
    }

}
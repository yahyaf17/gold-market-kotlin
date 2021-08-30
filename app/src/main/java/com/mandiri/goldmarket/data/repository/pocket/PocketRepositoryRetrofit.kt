package com.mandiri.goldmarket.data.repository.pocket

import android.util.Log
import com.mandiri.goldmarket.data.remote.api.PocketApi
import com.mandiri.goldmarket.data.remote.request.pocket.EditPocketRequest
import com.mandiri.goldmarket.data.remote.request.pocket.PocketRequest
import com.mandiri.goldmarket.data.remote.response.pocket.PocketResponse
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import kotlinx.coroutines.withTimeout
import okhttp3.Response
import java.lang.Exception
import javax.inject.Inject

class PocketRepositoryRetrofit @Inject constructor(private val pocketApi: PocketApi,
                               private val sharedPreferences: CustomSharedPreferences): PocketRepository {

    override suspend fun createPocket(request: PocketRequest): PocketResponse? {
        return try {
            withTimeout(7000) {
                val response = pocketApi.createPocket(request)
                if (response.isSuccessful) {
                    Log.d("PocketRepo", "createPocket: ${response.body()}")
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("PocketRepo", "createPocketFailed: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun getPocketById(pocketId: String): PocketResponse? {
        return try {
            withTimeout(7000) {
                val response = pocketApi.findPocketById(pocketId)
                if (response.isSuccessful) {
                    Log.d("PocketRepo", "getPocketById: ${response.body()}")
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("PocketRepo", "getPocketByIdFailed: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun getAllCustomerPockets(): List<PocketResponse>? {
        return try {
            withTimeout(7000) {
                val customerId = sharedPreferences.retrieveValue(CustomSharedPreferences.Key.CUSTOMER_ID)
                val response = pocketApi.getAllCustomerPockets(customerId ?: "null")
                if (response.isSuccessful) {
                    Log.d("PocketRepo", "customerPockets: ${response.body()}")
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("PocketRepo", "customerPocketsFailed: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun getAllCustomerPocketsByProduct(productId: Int): List<PocketResponse>? {
        return try {
            withTimeout(7000) {
                val customerId = sharedPreferences.retrieveValue(CustomSharedPreferences.Key.CUSTOMER_ID)
                val response = pocketApi.getAllCustomerPockets(customerId ?: "null")
                if (response.isSuccessful) {
                    Log.d("PocketRepo", "customerPockets: ${response.body()}")
                    response.body()?.filter { p -> p.product.id == productId }
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("PocketRepo", "customerPocketsFailed: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun editPocketName(request: EditPocketRequest): PocketResponse? {
        return try {
            withTimeout(20000) {
                val response = pocketApi.editPocketName(request)
                if (response.isSuccessful) {
                    Log.d("PocketRepo", "changePocket: ${response.body()}")
                    response.body()
                } else {
                    Log.d("PocketRepo", "editPocketName: TIMEOUT ")
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("PocketRepo", "changePocketFailed: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun deletePocket(pocketId: String): Int {
        return try {
            withTimeout(20000) {
                val response = pocketApi.deletePocket(pocketId)
                if (response.isSuccessful) {
                    Log.d("PocketRepo", "deletePocket: Success ${response.code()}")
                    response.code()
                } else {
                    Log.d("PocketRepo", "deletePocket: TIMEOUT ${response.code()}")
                    response.code()
                }
            }
        } catch (e: Exception) {
            Log.d("PocketRepo", "deletePocketFailed: ${e.localizedMessage} ")
        }
    }

}
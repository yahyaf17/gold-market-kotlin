package com.mandiri.goldmarket.data.repository.retrofit

import android.util.Log
import com.mandiri.goldmarket.data.remote.api.history.HistoryApi
import com.mandiri.goldmarket.data.remote.response.history.Content
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import kotlinx.coroutines.withTimeout
import java.lang.Exception

class HistoryRetrofitRepository(private val historyApi: HistoryApi,
                                private val sharedPreferences: CustomSharedPreferences) {

    suspend fun getCustomerHistory(): List<Content>? {
        return try {
            withTimeout(20000) {
                val customerId = sharedPreferences.retrieveString(CustomSharedPreferences.Key.CUSTOMER_ID)
                val response = historyApi.getPurchaseHistoryByCustomer(customerId!!)
                if (response.isSuccessful) {
                    Log.d("HistoryRepo", "Success: ${response.body()}")
                    response.body()?.content
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("HistoryRepo", "Failed: ${e.localizedMessage}")
            null
        }
    }

}
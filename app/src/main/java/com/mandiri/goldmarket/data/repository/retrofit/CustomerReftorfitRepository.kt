package com.mandiri.goldmarket.data.repository.retrofit

import android.util.Log
import com.mandiri.goldmarket.data.remote.api.customer.CustomerApi
import com.mandiri.goldmarket.data.remote.request.customer.CustomerRequest
import com.mandiri.goldmarket.data.remote.response.customer.CustomerResponse
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import kotlinx.coroutines.withTimeout
import java.lang.Exception

class CustomerReftorfitRepository(private val customerApi: CustomerApi,
                                  private val sharedPref: CustomSharedPreferences
) {

    suspend fun findCustomerById(): CustomerResponse? {
        return try {
            withTimeout(7000) {
                val customerId = sharedPref.retrieveString(CustomSharedPreferences.Key.CUSTOMER_ID)
                val response = customerApi.getCustomerById(customerId ?: "null")
                if (response.isSuccessful) {
                    Log.d("CustomerRepo", "customerById: ${response.body()!!.userName}")
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("CustomerRepo", "customerError: ${e.localizedMessage}")
            null
        }
    }

    suspend fun updateCustomerData(customerRequest: CustomerRequest): CustomerResponse? {
        return try {
            withTimeout(7000) {
                val response = customerApi.updateCustomerData(customerRequest)
                if (response.isSuccessful) {
                    Log.d("CustomerRepo", "updateData: ${response.body()}")
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("CustomerRepo", "updateDataError: ${e.localizedMessage}")
            null
        }
    }

}
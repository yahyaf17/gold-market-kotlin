package com.mandiri.goldmarket.data.repository.customer

import android.util.Log
import com.mandiri.goldmarket.data.remote.api.CustomerApi
import com.mandiri.goldmarket.data.remote.request.customer.CustomerRequest
import com.mandiri.goldmarket.data.remote.response.customer.CustomerResponse
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import kotlinx.coroutines.withTimeout
import java.lang.Exception
import javax.inject.Inject

class CustomerRepositoryRetrofit @Inject constructor(private val customerApi: CustomerApi,
                                 private val sharedPref: CustomSharedPreferences
): CustomerRepository {

    override suspend fun findCustomerById(): CustomerResponse? {
        return try {
            withTimeout(7000) {
                val customerId = sharedPref.retrieveValue(CustomSharedPreferences.Key.CUSTOMER_ID)
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

    override suspend fun updateCustomerData(customerRequest: CustomerRequest): CustomerResponse? {
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
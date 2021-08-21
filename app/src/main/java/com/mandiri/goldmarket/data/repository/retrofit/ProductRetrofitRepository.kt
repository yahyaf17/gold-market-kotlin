package com.mandiri.goldmarket.data.repository.retrofit

import android.util.Log
import com.mandiri.goldmarket.data.remote.api.product.ProductApi
import com.mandiri.goldmarket.data.remote.response.product.ProductResponse
import kotlinx.coroutines.withTimeout
import java.lang.Exception

class ProductRetrofitRepository(private val productApi: ProductApi) {

    suspend fun getProductById(productId: Int): ProductResponse? {
        return try {
            withTimeout(7000) {
                val response = productApi.getProductById(productId)
                if (response.isSuccessful) {
                    Log.d("ProductRepo", "getProduct: ${response.body()}")
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("ProductRepo", "getProductFailed: ${e.localizedMessage}")
            null
        }
    }

}
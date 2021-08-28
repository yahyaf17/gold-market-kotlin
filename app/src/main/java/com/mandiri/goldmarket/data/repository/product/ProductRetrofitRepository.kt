package com.mandiri.goldmarket.data.repository.product

import android.util.Log
import com.mandiri.goldmarket.data.remote.api.ProductApi
import com.mandiri.goldmarket.data.remote.response.product.AllProductResponse
import com.mandiri.goldmarket.data.remote.response.product.ProductResponse
import kotlinx.coroutines.withTimeout
import java.lang.Exception
import javax.inject.Inject

class ProductRetrofitRepository @Inject constructor(private val productApi: ProductApi): ProductRepository {

    override suspend fun getProductById(productId: Int): ProductResponse? {
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

    override suspend fun getAllProducts(): List<ProductResponse>? {
        return try {
            withTimeout(300000) {
                val response = productApi.getAllProducts()
                if (response.isSuccessful) {
                    Log.d("ProductRepo", "getAllProduct: ${response.body()}")
                    response.body()
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.d("ProductRepo", "getAllProductFailed: ${e.localizedMessage}")
            null
        }
    }

}
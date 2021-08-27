package com.mandiri.goldmarket.data.repository.product

import com.mandiri.goldmarket.data.remote.response.product.AllProductResponse
import com.mandiri.goldmarket.data.remote.response.product.ProductResponse

interface ProductRepository {
    suspend fun getProductById(productId: Int): ProductResponse?
    suspend fun getAllProducts(): List<ProductResponse>?
}
package com.mandiri.goldmarket.data.repository.pocket

import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.data.remote.request.pocket.PocketRequest
import com.mandiri.goldmarket.data.remote.response.pocket.PocketResponse
import java.math.BigDecimal

interface PocketRepository {
    suspend fun createPocket(request: PocketRequest): PocketResponse?
    suspend fun getPocketById(pocketId: String): PocketResponse?
    suspend fun getAllCustomerPockets(): List<PocketResponse>?
    suspend fun getAllCustomerPocketsByProduct(productId: Int): List<PocketResponse>?
}
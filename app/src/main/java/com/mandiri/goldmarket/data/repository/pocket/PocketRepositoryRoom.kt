package com.mandiri.goldmarket.data.repository.pocket

import com.mandiri.goldmarket.data.db.AppDatabase
import com.mandiri.goldmarket.data.model.CustomerWithPockets
import com.mandiri.goldmarket.data.model.Pocket

class PocketRepositoryRoom(private val app: AppDatabase) {

    suspend fun createNewPocket(pocket: Pocket) = app.pocketDao().insert(pocket)

    suspend fun findPocketByCustomer(customerId: Int): List<CustomerWithPockets>? = app.pocketDao().getCustomerPockets(customerId)

    suspend fun findPocketById(pocketId: Int) = app.pocketDao().getPocketById(pocketId)

    suspend fun getTotalPocketCount(customerId: Int) = app.pocketDao().getPocketCount(customerId)

    suspend fun getTotalBalanceByCustomer(customerId: Int) = app.pocketDao().getTotalPocketBalance(customerId)
}
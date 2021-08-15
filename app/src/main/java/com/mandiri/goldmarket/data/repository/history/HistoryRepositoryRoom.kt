package com.mandiri.goldmarket.data.repository.history

import com.mandiri.goldmarket.data.db.AppDatabase
import com.mandiri.goldmarket.data.model.History

class HistoryRepositoryRoom(private val app: AppDatabase) {

    suspend fun getAllHistoryByCustomer(customerId: Int) =
        app.historyDao().findAllHistoryByCustomer(customerId)

}
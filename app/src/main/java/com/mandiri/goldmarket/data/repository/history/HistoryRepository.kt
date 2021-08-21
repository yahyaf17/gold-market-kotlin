package com.mandiri.goldmarket.data.repository.history

import com.mandiri.goldmarket.data.model.History
import com.mandiri.goldmarket.data.remote.response.history.Content

interface HistoryRepository {
    suspend fun getCustomerHistory(): List<Content>?
}
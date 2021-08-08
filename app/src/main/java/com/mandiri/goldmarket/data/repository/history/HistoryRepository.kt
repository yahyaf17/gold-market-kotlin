package com.mandiri.goldmarket.data.repository.history

import com.mandiri.goldmarket.data.model.History

interface HistoryRepository {
    fun findAllHistory(): List<History>
    fun deleteHistory(positon: Int): History
    fun addHistory(history: History): History
}
package com.mandiri.goldmarket.data.repository.history

import com.mandiri.goldmarket.data.model.History
import java.math.BigDecimal
import java.time.Instant
import java.util.*

class HistoryRepositoryImpl: HistoryRepository {

    override fun findAllHistory(): List<History> {
        return historyDb
    }

    override fun deleteHistory(positon: Int): History {
        return historyDb.removeAt(positon)
    }

    companion object {
        var historyDb = mutableListOf(
            History(
                "Gold",
                "Buy",
                1.0,
                BigDecimal(850000),
                "Grasberg",
                Date()),
            History(
                "Gold",
                "Buy",
                2.1,
                BigDecimal(1640000),
                "Grasberg",
                Date())
        )
    }
}
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

    override fun addHistory(history: History): History {
        historyDb.add(history)
        return history
    }

    companion object {
        var historyDb = mutableListOf(
            History(
                product = "Gold",
                purchaseType = "Buy",
                amount = 1.0,
                totalPrice = 850000.0,
                pocketName = "Grasberg",
                purchaseDate = Date(),
                customerId = 0
            ),
            History(
                product = "Gold",
                purchaseType = "Buy",
                amount = 2.1,
                totalPrice = 1640000.0,
                pocketName = "Grasberg",
                purchaseDate = Date(),
                customerId = 0
            ),
            History(
                product = "Gold",
                purchaseType = "Sell",
                amount = 0.2,
                totalPrice = 166700.0,
                pocketName = "Grass root",
                purchaseDate = Date(),
                customerId = 0
            )
        )
    }
}
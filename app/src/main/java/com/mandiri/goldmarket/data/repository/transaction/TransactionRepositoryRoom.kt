package com.mandiri.goldmarket.data.repository.transaction

import com.mandiri.goldmarket.data.db.AppDatabase
import com.mandiri.goldmarket.data.model.History
import com.mandiri.goldmarket.data.model.Pocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TransactionRepositoryRoom(private val app: AppDatabase) {

    suspend fun performPurchase(customerId: Int,
                                pocketId: Int,
                                amount: Double,
                                totalPrice: Double,
                                purchaseType: String
    ) {
        with(app) {
            runInTransaction {
                CoroutineScope(Dispatchers.IO).launch {
                    val pocket = pocketDao().getPocketById(pocketId)
                    var fixAmount = 0.0
                    var fixTotalPrice = 0.0
                    if (purchaseType == "Buy") {
                        fixAmount = pocket.amount + amount
                        fixTotalPrice = pocket.totalPrice + totalPrice
                    }
                    if (purchaseType == "Sell") {
                        fixAmount =pocket.amount - amount
                        fixTotalPrice = pocket.totalPrice - totalPrice
                    }
                    pocketDao().update(Pocket(
                        pocketId,
                        pocket.name,
                        fixAmount,
                        pocket.product,
                        fixTotalPrice,
                        customerId,
                        1
                    ))
                    historyDao().insert(History(
                        product = "Gold",
                        purchaseType = purchaseType,
                        amount = fixAmount,
                        totalPrice = fixTotalPrice,
                        pocketName = pocket.name,
                        purchaseDate = Date(),
                        customerId = customerId
                    ))
                }
            }
        }
    }

    private fun getExactAmount(purchaseType: String, amount: Double): Double {
        if (purchaseType == "Buy") {
            return amount
        }
        if (purchaseType == "Sell") {
            return amount.times(-1)
        }
        return 0.0
    }

}
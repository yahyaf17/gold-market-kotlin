package com.mandiri.goldmarket.data.repository.pocket

import com.mandiri.goldmarket.data.model.Pocket
import java.math.BigDecimal

class PocketRepositoryImpl: PocketRepository {
    override fun findAll(): List<Pocket> {
        return pocketDb
    }

    override fun findById(pocketName: String): Pocket? {
        return pocketDb.find { pocket ->
            pocket.name == pocketName
        }
    }

    override fun editPocket(pocketName: String, newName: String): Pocket {
        val selectedPocket = findById(pocketName)
        selectedPocket!!.apply {
            name = newName
            amount = selectedPocket.amount
            totalPrice = selectedPocket.totalPrice
            product = selectedPocket.product
        }
        return selectedPocket
    }

    override fun countPocket(): Int {
        return pocketDb.size
    }

    override fun totalBalanceOfPocket(): BigDecimal {
        var totalBalance = BigDecimal(0.0)
        pocketDb.forEach {
            totalBalance += it.totalPrice
        }
        return totalBalance
    }

    override fun createNewPocket(name: String, product: String): Pocket {
        val newPocket = Pocket(name, product, 0.0, BigDecimal(0))
        pocketDb.add(newPocket)
        return newPocket
    }

    override fun addPocketTransaction(pocket: String, trx: String, amount: Double, price: BigDecimal): Pocket {
        val selectedPocket = findById(pocket)
        if (trx == "Buy") {
            selectedPocket!!.amount += amount
            selectedPocket!!.totalPrice += price
        }
        if (trx == "Sell") {
            selectedPocket!!.amount -= amount
            selectedPocket!!.totalPrice -= price
        }
        return selectedPocket!!
    }

    companion object {
        var pocketDb = mutableListOf(
            Pocket(
                "Grasberg",
                "Gold",
                3.1,
                BigDecimal(2490000),
            ),
        )
    }
}
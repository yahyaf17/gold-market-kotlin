package com.mandiri.goldmarket.data.repository.pocket

import com.mandiri.goldmarket.data.model.Pocket
import java.math.BigDecimal

interface PocketRepository {
    fun findAll(): List<Pocket>
    fun findById(pocketName: String): Pocket?
    fun editPocket(pocketName: String, newName: String): Pocket
    fun countPocket(): Int
    fun totalBalanceOfPocket(): BigDecimal
    fun createNewPocket(name: String, product: String): Pocket
}
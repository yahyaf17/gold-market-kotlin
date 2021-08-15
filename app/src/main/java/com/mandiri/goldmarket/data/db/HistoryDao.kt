package com.mandiri.goldmarket.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.mandiri.goldmarket.data.model.CustomerWithHistory
import com.mandiri.goldmarket.data.model.History

@Dao
interface HistoryDao: BaseDao<History> {

    @Transaction
    @Query("SELECT * FROM customer WHERE customer_id=:customerId")
    suspend fun findAllHistoryByCustomer(customerId: Int): List<CustomerWithHistory>

}
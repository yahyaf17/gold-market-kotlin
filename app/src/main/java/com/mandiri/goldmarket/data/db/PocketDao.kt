package com.mandiri.goldmarket.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.model.CustomerWithPockets
import com.mandiri.goldmarket.data.model.Pocket

@Dao
interface PocketDao: BaseDao<Pocket> {

    @Query("SELECT * FROM pocket where pocket_id=:id")
    suspend fun getPocketById(id: Int): Pocket

    @Transaction
    @Query("SELECT * FROM customer where customer_id=:customerId")
    suspend fun getCustomerPockets(customerId: Int): List<CustomerWithPockets>?

    @Query("SELECT COUNT(*) FROM pocket WHERE customer_owner_id=:customerId")
    suspend fun getPocketCount(customerId: Int): Int

    @Query("SELECT SUM(total_price) FROM pocket WHERE customer_owner_id=:customerId")
    suspend fun getTotalPocketBalance(customerId: Int): Double?
}
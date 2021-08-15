package com.mandiri.goldmarket.data.db

import androidx.room.Dao
import androidx.room.Query
import com.mandiri.goldmarket.data.model.Customer

@Dao
interface CustomerDao: BaseDao<Customer> {

    @Query("SELECT * FROM customer where customer_id=:id")
    suspend fun getCustomerById(id: Int): Customer?

    @Query("SELECT * FROM customer WHERE username=:username AND password=:password")
    suspend fun loginCustomer(username: String, password: String): Customer?

}
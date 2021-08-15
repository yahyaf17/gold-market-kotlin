package com.mandiri.goldmarket.data.repository.customer

import com.mandiri.goldmarket.data.db.AppDatabase
import com.mandiri.goldmarket.data.model.Customer

class CustomerRepositoryRoom(private val app: AppDatabase) {

    suspend fun registerCustomer(customer: Customer) = app.customerDao().insert(customer)

    suspend fun loginCustomer(username: String, password: String): Customer? =
        app.customerDao().loginCustomer(username, password)

    suspend fun getCustomerById(id: Int): Customer? = app.customerDao().getCustomerById(id)

    suspend fun updateCustomer(customer: Customer) = app.customerDao().update(customer)

}
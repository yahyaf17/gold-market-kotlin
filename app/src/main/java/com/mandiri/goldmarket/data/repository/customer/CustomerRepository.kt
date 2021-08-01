package com.mandiri.goldmarket.data.repository.customer

import com.mandiri.goldmarket.data.model.Customer

interface CustomerRepository {
    fun findAll(): List<Customer>
    fun findByUsername(username: String): Customer?
    fun addCustomer(customer: Customer): Customer
    fun updateCustomer(customer: Customer): Customer
    fun customerLogin(username: String, password: String): Customer?
}
package com.mandiri.goldmarket.data.repository.customer

import com.mandiri.goldmarket.data.model.Customer

class CustomerRepositoryImpl: CustomerRepository {

    override fun findAll(): List<Customer> {
        return customerDb
    }

    override fun findByUsername(username: String): Customer? {
        return customerDb.find { customer: Customer ->
            customer.username == username
        }
    }

    override fun addCustomer(customer: Customer): Customer {
        customerDb.add(customer)
        return customer
    }

    override fun updateCustomer(customer: Customer): Customer {
        val selectedCustomer = findByUsername(customer.username)
        return selectedCustomer!!.apply {
            firstName = customer.firstName
            lastName = customer.lastName
            username = customer.username
            email = customer.email
            password = customer.password
        }
    }

    override fun customerLogin(username: String, password: String): Customer? {
        return customerDb.find { customer: Customer ->
            customer.username == username && customer.password == password
        }
    }

    companion object{
        var customerDb = mutableListOf(
            Customer("tes", "tes", "tes@mail.com", "tes", "enigma")
        )
    }
}
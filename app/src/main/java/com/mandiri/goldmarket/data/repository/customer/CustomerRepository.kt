package com.mandiri.goldmarket.data.repository.customer

import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.remote.request.customer.CustomerRequest
import com.mandiri.goldmarket.data.remote.response.customer.CustomerResponse

interface CustomerRepository {
    suspend fun updateCustomerData(customerRequest: CustomerRequest): CustomerResponse?
    suspend fun findCustomerById(): CustomerResponse?
}
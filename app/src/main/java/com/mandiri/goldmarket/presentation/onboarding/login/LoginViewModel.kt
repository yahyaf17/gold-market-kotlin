package com.mandiri.goldmarket.presentation.onboarding.login

import androidx.lifecycle.ViewModel
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepository

class LoginViewModel(private val repository: CustomerRepository): ViewModel() {

    fun loginCustomer(username: String, password: String): Customer? {
        return repository.customerLogin(username, password)
    }

}
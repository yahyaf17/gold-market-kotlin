package com.mandiri.goldmarket.presentation.onboarding.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepository

class RegisterViewModel(private val repository: CustomerRepository): ViewModel() {

    fun addCustomer(customer: Customer) {
        repository.addCustomer(customer)
        Log.d("RegisterVM", "addCustomer: $customer")
        Log.d("RegisterVM", "addCustomer: ${repository.findAll()}")
    }

}
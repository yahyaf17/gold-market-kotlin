package com.mandiri.goldmarket.presentation.onboarding.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepository
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val roomRepository: CustomerRepositoryRoom
): ViewModel() {

    fun registerCustomerRoom(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.registerCustomer(customer)
        }
    }

}
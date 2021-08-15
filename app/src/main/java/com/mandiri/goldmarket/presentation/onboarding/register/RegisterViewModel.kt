package com.mandiri.goldmarket.presentation.onboarding.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepository
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: CustomerRepository,
                        private val roomRepository: CustomerRepositoryRoom
): ViewModel() {

    fun addCustomer(customer: Customer) {
        repository.addCustomer(customer)
        Log.d("RegisterVM", "addCustomer: $customer")
    }

    fun registerCustomerRoom(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.registerCustomer(customer)
        }
    }

}
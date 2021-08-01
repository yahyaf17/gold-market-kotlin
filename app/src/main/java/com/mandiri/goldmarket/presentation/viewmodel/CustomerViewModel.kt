package com.mandiri.goldmarket.presentation.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepository
import com.mandiri.goldmarket.utils.EventResult
import java.lang.Exception

class CustomerViewModel(private val repository: CustomerRepository): ViewModel() {

    private var _customerLiveData = MutableLiveData<EventResult>(EventResult.Idle)

    val customerLiveData: LiveData<EventResult>
        get() = _customerLiveData

    private fun getAllCustomer() = repository.findAll()

    private fun getCustomerInfo(username: String) {
        _customerLiveData.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val customer = findCustomerByUsername(username)
                _customerLiveData.value = EventResult.Success(customer!!)
                Log.d("CustomerVM", "getCustomerInfo: Success")
            } catch (e: Exception) {
                _customerLiveData.value = EventResult.ErrorMessage("Can't Retrieve Customer Data")
                Log.d("CustomerVM", "getCustomerInfo: Error")
            }
        }, 1000)

    }

    fun getCustomer(username: String) {
        getCustomerInfo(username)
    }

    fun findCustomerByUsername(username: String): Customer? {
        return repository.findByUsername(username)
    }

    fun addCustomer(customer: Customer) {
        repository.addCustomer(customer)
    }

    fun updateCustomerData(customer: Customer) {
        repository.updateCustomer(customer)
    }

    fun loginCustomer(username: String, password: String): Customer? {
        return repository.customerLogin(username, password)
    }

}
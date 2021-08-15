package com.mandiri.goldmarket.presentation.onboarding.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRoom
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val customerRepo: CustomerRepositoryRoom
): ViewModel() {

    private val _response = MutableLiveData<EventResult>(EventResult.Idle)
    val response: LiveData<EventResult>
        get() = _response

    fun loginCustomerRoom(username: String, password: String) {
        _response.postValue(EventResult.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val customer = customerRepo.loginCustomer(username, password)
            if (customer == null) {
                _response.postValue(EventResult.ErrorMessage("Failed Get Customer"))
                return@launch
            }
            _response.postValue(EventResult.Success(customer))
        }
    }

}
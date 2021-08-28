package com.mandiri.goldmarket.presentation.maintab.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.remote.request.customer.CustomerRequest
import com.mandiri.goldmarket.data.remote.response.customer.CustomerResponse
import com.mandiri.goldmarket.data.repository.customer.CustomerRepository
import com.mandiri.goldmarket.data.repository.pocket.PocketRepository
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileViewModel(private val customerRetrofit: CustomerRepository,
                       private val pocketRepository: PocketRepository,
): ViewModel() {

    private val _customerLiveData = MutableLiveData<CustomerResponse>()
    private val _pocketCountLiveData = MutableLiveData<Int>()
    private val _totalBalanceLiveData = MutableLiveData<Int>()

    private val _response = MutableLiveData<EventResult>(EventResult.Idle)
    val response: LiveData<EventResult>
        get() = _response

    val pocketCountLiveData: LiveData<Int>
        get() = _pocketCountLiveData
    val customerLiveData: LiveData<CustomerResponse>
        get() = _customerLiveData
    val totalBalanceLiveData: LiveData<Int>
        get() = _totalBalanceLiveData

    fun findCustomerById() {
        viewModelScope.launch(Dispatchers.IO) {
            val customer = customerRetrofit.findCustomerById()
            _customerLiveData.postValue(customer)
        }
    }

    fun updateCustomerData(customer: CustomerRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRetrofit.updateCustomerData(customer)
        }
    }

    private fun profileInfoObservable() {
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(EventResult.Loading)
            delay(1000)
            val customer = customerRetrofit.findCustomerById()
            if (customer == null) {
                _response.postValue(EventResult.ErrorMessage("Can't Retrieve Customer Data"))
                Log.d("CustomerVM", "getCustomerInfo: Error")
                return@launch
            }
            _customerLiveData.postValue(customer)
            val pocketCount = pocketRepository.getAllCustomerPockets()?.size ?: 0
            _pocketCountLiveData.postValue(pocketCount)
            val totalBalance = pocketRepository.getAllCustomerPockets()
                ?.map { it.totalAmount }
                ?.sum()
            _totalBalanceLiveData.postValue(totalBalance ?: 0)
            _response.postValue(EventResult.Success())
            Log.d("CustomerVM", "getCustomerInfo: Success")
        }
    }

    fun getProfileInfo() {
        profileInfoObservable()
    }
}
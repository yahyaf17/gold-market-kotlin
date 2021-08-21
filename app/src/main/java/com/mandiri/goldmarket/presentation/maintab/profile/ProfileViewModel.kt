package com.mandiri.goldmarket.presentation.maintab.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.remote.request.customer.CustomerRequest
import com.mandiri.goldmarket.data.remote.response.customer.CustomerResponse
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRoom
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryRoom
import com.mandiri.goldmarket.data.repository.retrofit.CustomerReftorfitRepository
import com.mandiri.goldmarket.data.repository.retrofit.PocketRetrofitRepository
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileViewModel(private val customerReftorfitRepository: CustomerReftorfitRepository,
                       private val pocketRetrofitRepository: PocketRetrofitRepository,
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
            val customer = customerReftorfitRepository.findCustomerById()
            _customerLiveData.postValue(customer)
        }
    }

    fun updateCustomerData(customer: CustomerRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            customerReftorfitRepository.updateCustomerData(customer)
        }
    }

    private fun profileInfoObservable(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(EventResult.Loading)
            delay(1000)
            val customer = customerReftorfitRepository.findCustomerById()
            if (customer == null) {
                _response.postValue(EventResult.ErrorMessage("Can't Retrieve Customer Data"))
                Log.d("CustomerVM", "getCustomerInfo: Error")
                return@launch
            }
            _customerLiveData.postValue(customer)
            val pocketCount = pocketRetrofitRepository.getAllCustomerPockets()?.size ?: 0
            _pocketCountLiveData.postValue(pocketCount)
            val totalBalance = pocketRetrofitRepository.getAllCustomerPockets()
                ?.map { it.totalAmount }
                ?.sum()
            _totalBalanceLiveData.postValue(totalBalance ?: 0)
            _response.postValue(EventResult.Success())
            Log.d("CustomerVM", "getCustomerInfo: Success")
        }
    }

    fun getProfileInfo(id: Int) {
        profileInfoObservable(id)
    }
}
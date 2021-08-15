package com.mandiri.goldmarket.presentation.maintab.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRoom
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryImpl
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryRoom
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ProfileViewModel(private val customerRepo: CustomerRepositoryRoom,
                       private val pocketRoom: PocketRepositoryRoom
): ViewModel() {

    private val _customerLiveData = MutableLiveData(Customer(0, " ", " ", " ", " ", ""))
    private val _pocketCountLiveData = MutableLiveData<Int>()
    private val _totalBalanceLiveData = MutableLiveData<Double>()

    private val _response = MutableLiveData<EventResult>(EventResult.Idle)
    val response: LiveData<EventResult>
        get() = _response

    val pocketCountLiveData: LiveData<Int>
        get() = _pocketCountLiveData
    val customerLiveData: LiveData<Customer>
        get() = _customerLiveData
    val totalBalanceLiveData: LiveData<Double>
        get() = _totalBalanceLiveData

    fun findCustomerById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val customer = customerRepo.getCustomerById(id)
            _customerLiveData.postValue(customer)
        }
    }

    fun updateCustomerData(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepo.updateCustomer(customer)
        }
    }

    private fun profileInfoObservable(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(EventResult.Loading)
            delay(1000)
            val customer = customerRepo.getCustomerById(id)
            if (customer == null) {
                _response.postValue(EventResult.ErrorMessage("Can't Retrieve Customer Data"))
                Log.d("CustomerVM", "getCustomerInfo: Error")
                return@launch
            }
            _customerLiveData.postValue(customer)
            val pocketCount = pocketRoom.getTotalPocketCount(id)
            _pocketCountLiveData.postValue(pocketCount)
            val totalBalance = pocketRoom.getTotalBalanceByCustomer(id)
            _totalBalanceLiveData.postValue(totalBalance ?: 0.0)
            _response.postValue(EventResult.Success())
            Log.d("CustomerVM", "getCustomerInfo: Success")
        }
    }

    fun getProfileInfo(id: Int) {
        profileInfoObservable(id)
    }
}
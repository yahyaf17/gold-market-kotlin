package com.mandiri.goldmarket.presentation.maintab.profile

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryImpl
import com.mandiri.goldmarket.utils.EventResult
import java.lang.Exception
import java.math.BigDecimal

class ProfileViewModel(private val customerRepo: CustomerRepositoryImpl, private val  pocketRepo: PocketRepositoryImpl): ViewModel() {

    private val _customerLiveData = MutableLiveData(Customer(" ", " ", " ", " ", " "))
    private val _pocketCountLiveData = MutableLiveData<Int>()
    private val _totalBalanceLiveData = MutableLiveData(BigDecimal(0))

    private val _response = MutableLiveData<EventResult>(EventResult.Idle)
    val response: LiveData<EventResult>
        get() = _response

    val pocketCountLiveData: LiveData<Int>
        get() = _pocketCountLiveData
    val customerLiveData: LiveData<Customer>
        get() = _customerLiveData
    val totalBalanceLiveData: LiveData<BigDecimal>
        get() = _totalBalanceLiveData

    fun findCustomerByUsername(username: String): Customer? {
        return customerRepo.findByUsername(username)
    }

    fun updateCustomerData(customer: Customer) {
        customerRepo.updateCustomer(customer)
    }

    private fun profileInfoObserveable(username: String) {
        _response.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val customer = findCustomerByUsername(username)
                _customerLiveData.value = customer
                val pocketCount = pocketRepo.countPocket()
                _pocketCountLiveData.value = pocketCount
                val totalBalance = pocketRepo.totalBalanceOfPocket()
                _totalBalanceLiveData.value = totalBalance
                _response.value = EventResult.Success()
                Log.d("CustomerVM", "getCustomerInfo: Success")
            } catch (e: Exception) {
                _response.value = EventResult.ErrorMessage("Can't Retrieve Customer Data")
                Log.d("CustomerVM", "getCustomerInfo: Error")
            }
        }, 1000)
    }

    fun getProfileInfo(username: String) {
        profileInfoObserveable(username)
    }
}
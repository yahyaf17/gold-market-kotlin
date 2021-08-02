package com.mandiri.goldmarket.presentation.maintab.profile

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryImpl
import com.mandiri.goldmarket.utils.EventResult
import java.lang.Exception

class ProfileViewModel(private val customerRepo: CustomerRepositoryImpl, private val  pocketRepo: PocketRepositoryImpl): ViewModel() {

    private var _customerLiveData = MutableLiveData<EventResult>(EventResult.Idle)
    private var _pocketCountLiveData = MutableLiveData<EventResult>(EventResult.Idle)
    private var _totalBalanceLiveData = MutableLiveData<EventResult>(EventResult.Idle)

    val pocketCountLiveData: LiveData<EventResult>
        get() = _pocketCountLiveData
    val customerLiveData: LiveData<EventResult>
        get() = _customerLiveData
    val totalBalanceLiveData: LiveData<EventResult>
        get() = _totalBalanceLiveData

    fun findCustomerByUsername(username: String): Customer? {
        return customerRepo.findByUsername(username)
    }

    fun updateCustomerData(customer: Customer) {
        customerRepo.updateCustomer(customer)
    }

    private fun customerInfoObservable(username: String) {
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

    private fun totalBalanceObservable() {
        _totalBalanceLiveData.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val totalBalance = pocketRepo.totalBalanceOfPocket()
                _totalBalanceLiveData.value = EventResult.Success(totalBalance)
            } catch (e: Exception) {
                _totalBalanceLiveData.value = EventResult.ErrorMessage("Can't Retrieve Customer Data")
            }
        }, 1000)
    }

    private fun pocketCountObservable() {
        _pocketCountLiveData.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val pocketCount = pocketRepo.countPocket()
                _pocketCountLiveData.value = EventResult.Success(pocketCount)
            } catch (e: Exception) {
                _totalBalanceLiveData.value = EventResult.ErrorMessage("Can't Retrieve Customer Data")
            }
        }, 1000)
    }

    fun getCustomerInfo(username: String) {
        customerInfoObservable(username)
    }

    fun getBalanceInfo() {
        totalBalanceObservable()
    }

    fun getPocketCountInfo() {
        pocketCountObservable()
    }
}
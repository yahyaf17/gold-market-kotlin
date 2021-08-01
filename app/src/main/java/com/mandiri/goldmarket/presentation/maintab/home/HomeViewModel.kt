package com.mandiri.goldmarket.presentation.maintab.home

import android.os.Handler
import android.os.Looper
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

class HomeViewModel(val customerRepo: CustomerRepositoryImpl, val pocketRepo: PocketRepositoryImpl): ViewModel() {

    private var _customerLiveData = MutableLiveData<EventResult>(EventResult.Idle)
    private var _totalBalanceLiveData = MutableLiveData<EventResult>(EventResult.Idle)
    private var _pocketSelected = MutableLiveData<EventResult>(EventResult.Idle)

    val customerLiveData: LiveData<EventResult>
        get() = _customerLiveData

    val totalBalanceLiveData: LiveData<EventResult>
        get() = _totalBalanceLiveData

    val pocketSelectedLiveData: LiveData<EventResult>
        get() = _pocketSelected

    fun findCustomerByUsername(username: String): Customer? {
        return customerRepo.findByUsername(username)
    }

    fun findPocketByName(name: String): Pocket? {
        return pocketRepo.findById(name)
    }

    fun getPocketTotalBalance(): BigDecimal {
        return pocketRepo.totalBalanceOfPocket()
    }

    private fun getCustomerInfo(username: String) {
        _customerLiveData.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val customer = findCustomerByUsername(username)
                _customerLiveData.value = EventResult.Success(customer!!)
            } catch (e: Exception) {
                _customerLiveData.value = EventResult.ErrorMessage("Can't Retrieve Customer Data")
            }
        }, 1000)
    }

    private fun getPocketSelected(name: String) {
        _pocketSelected.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val pocketSelected = findPocketByName(name)
                _pocketSelected.value = EventResult.Success(pocketSelected!!)
            } catch (e: Exception) {
                _pocketSelected.value = EventResult.ErrorMessage("Can't Retrieve Customer Data")
            }
        }, 1000)
    }

    private fun getTotalBalanceInfo() {
        _totalBalanceLiveData.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val totalBalance = getPocketTotalBalance()
                _totalBalanceLiveData.value = EventResult.Success(totalBalance)
            } catch (e: Exception) {
                _totalBalanceLiveData.value = EventResult.ErrorMessage("Can't Retrieve Customer Data")
            }
        }, 1000)
    }

    fun getCustomer(username: String) {
        getCustomerInfo(username)
    }

    fun getBalanceInfo() {
        getTotalBalanceInfo()
    }

    fun getCurrentPocket(name: String) {
        getPocketSelected(name)
    }

}
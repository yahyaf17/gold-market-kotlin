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

class HomeViewModel(private val customerRepo: CustomerRepositoryImpl, private val pocketRepo: PocketRepositoryImpl): ViewModel() {

    private var _customerLiveData = MutableLiveData<EventResult>(EventResult.Idle)
    private var _totalBalanceLiveData = MutableLiveData<EventResult>(EventResult.Idle)
    private var _pocketSelectedLiveData = MutableLiveData<EventResult>(EventResult.Idle)
    private var _pocketsLiveData = MutableLiveData<EventResult>(EventResult.Idle)

    val customerLiveData: LiveData<EventResult>
        get() = _customerLiveData

    val totalBalanceLiveData: LiveData<EventResult>
        get() = _totalBalanceLiveData

    val pocketSelectedLiveData: LiveData<EventResult>
        get() = _pocketSelectedLiveData

    val pocketsLiveData: LiveData<EventResult>
        get() = _pocketsLiveData

    private fun findCustomerByUsername(username: String): Customer? {
        return customerRepo.findByUsername(username)
    }

    private fun findPocketByName(name: String): Pocket? {
        return pocketRepo.findById(name)
    }

    private fun getPocketTotalBalance(): BigDecimal {
        return pocketRepo.totalBalanceOfPocket()
    }

    fun createNewPocket(name: String): Pocket {
        return pocketRepo.createNewPocket(name, "Gold")
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
        _pocketSelectedLiveData.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val pocketSelected = findPocketByName(name)
                _pocketSelectedLiveData.value = EventResult.Success(pocketSelected!!)
            } catch (e: Exception) {
                _pocketSelectedLiveData.value = EventResult.ErrorMessage("Can't Retrieve Pocket Selected Data")
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
                _totalBalanceLiveData.value = EventResult.ErrorMessage("Can't Retrieve Balance Data")
            }
        }, 1000)
    }

    private fun getAllPockets() {
        _pocketsLiveData.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val pockets = pocketRepo.findAll()
                _pocketsLiveData.value = EventResult.Success(pockets)
            } catch (e: Exception) {
                _pocketsLiveData.value = EventResult.ErrorMessage("Can't Retrieve Pockets data")
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

    fun getPockets() {
        getAllPockets()
    }

}
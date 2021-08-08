package com.mandiri.goldmarket.presentation.maintab.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.data.model.Product
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryImpl
import com.mandiri.goldmarket.data.repository.product.ProductRepositoryImpl
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import java.math.BigDecimal

class HomeViewModel(private val customerRepo: CustomerRepositoryImpl,
                    private val pocketRepo: PocketRepositoryImpl,
                    private val productRepo: ProductRepositoryImpl): ViewModel() {

    private var _customerLiveData = MutableLiveData<Customer>()
    private var _totalBalanceLiveData = MutableLiveData<BigDecimal>()
    private var _pocketSelectedLiveData = MutableLiveData<Pocket>()
    private var _pocketsLiveData = MutableLiveData<List<Pocket>>()
    private var _productLiveData = MutableLiveData<Product>()
    private val _response = MutableLiveData<EventResult>(EventResult.Idle)

    val response: LiveData<EventResult>
        get() = _response
    val customerLiveData: LiveData<Customer>
        get() = _customerLiveData
    val totalBalanceLiveData: LiveData<BigDecimal>
        get() = _totalBalanceLiveData
    val pocketSelectedLiveData: LiveData<Pocket>
        get() = _pocketSelectedLiveData
    val productLiveData: LiveData<Product>
        get() = _productLiveData

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

    private fun homeObservable(username: String, pocketName: String, idProduct: String) {
        _response.value = EventResult.Loading
        try {
            val customer = findCustomerByUsername(username)
            _customerLiveData.value = customer
            val pocketSelected = findPocketByName(pocketName)
            _pocketSelectedLiveData.value = pocketSelected
            val totalBalance = getPocketTotalBalance()
            _totalBalanceLiveData.value = totalBalance
            val pockets = pocketRepo.findAll()
            _pocketsLiveData.value = pockets
            val product = productRepo.findProductById(idProduct)
            _productLiveData.value = product
            _response.value = EventResult.Success(pockets)
            Log.d("HomeVM", "homeObserveable: Success")
        } catch (e: Exception) {
            _response.value = EventResult.ErrorMessage("Can't Retrieve Customer Data")
        }
    }

    fun getHomeInfo(username: String, pocketName: String, productId: String) {
        homeObservable(username, pocketName, productId)
    }

    private fun getPocketSelected(name: String) {
        _response.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val pocketSelected = findPocketByName(name)
                _pocketSelectedLiveData.value = pocketSelected
                _response.value = EventResult.Success(pocketSelected)
            } catch (e: Exception) {
                _response.value = EventResult.ErrorMessage("Can't Retrieve Pocket Selected Data")
            }
        }, 1000)
    }

    fun getCurrentPocket(name: String) {
        getPocketSelected(name)
    }

}
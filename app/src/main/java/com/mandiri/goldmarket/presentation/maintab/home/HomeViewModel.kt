package com.mandiri.goldmarket.presentation.maintab.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.data.model.Product
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRoom
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryImpl
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryRoom
import com.mandiri.goldmarket.data.repository.product.ProductRepositoryImpl
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepo: ProductRepositoryImpl,
                    private val customerRepo: CustomerRepositoryRoom,
                    private val pocketRoom: PocketRepositoryRoom
): ViewModel() {

    private var _customerLiveData = MutableLiveData<Customer>()
    private var _totalBalanceLiveData = MutableLiveData<Double>()
    private var _pocketSelectedLiveData = MutableLiveData<Pocket>()
    private var _pocketsLiveData = MutableLiveData<List<Pocket>>()
    private var _productLiveData = MutableLiveData<Product>()
    private val _response = MutableLiveData<EventResult>(EventResult.Idle)

    val response: LiveData<EventResult>
        get() = _response
    val customerLiveData: LiveData<Customer>
        get() = _customerLiveData
    val totalBalanceLiveData: LiveData<Double>
        get() = _totalBalanceLiveData
    val pocketSelectedLiveData: LiveData<Pocket>
        get() = _pocketSelectedLiveData
    val productLiveData: LiveData<Product>
        get() = _productLiveData

    fun createNewPocketRoom(pocketName: String, custId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            pocketRoom.createNewPocket(Pocket(
                name = pocketName,
                amount = 0.0,
                product = "Gold",
                totalPrice = 0.0,
                pocketOwnerId = custId,
                pocketProduct = 1
            ))
        }
    }


    private fun homeObservable(customerId: Int, productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(EventResult.Loading)
            delay(1000)
            val customer = customerRepo.getCustomerById(customerId)
            val totalBalance = pocketRoom.getTotalBalanceByCustomer(customerId)
            val pockets = pocketRoom.findPocketByCustomer(customerId)
            val product = productRepo.findProductById(productId)
            if (customer == null) {
                Log.d("HomeVM", "homeObserveable: Success")
                _response.postValue(EventResult.ErrorMessage("Can't Retrieve Customer Data"))
                return@launch
            }
            _customerLiveData.postValue(customer)
            _totalBalanceLiveData.postValue(totalBalance ?: 0.0)
            _pocketsLiveData.postValue(pockets?.get(0)?.pocket)
            _productLiveData.postValue(product)
            _response.postValue(EventResult.Success(pockets?.get(0)?.pocket))
        }
    }

    fun getHomeInfo(idCustomer: Int, productId: Int) {
        homeObservable(idCustomer, productId)
    }

    private fun getPocketSelected(idPocket: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val pocketSelected = pocketRoom.findPocketById(idPocket)
            _pocketSelectedLiveData.postValue(pocketSelected)
        }
    }

    fun getCurrentPocket(idPocket: Int) {
        getPocketSelected(idPocket)
    }

}
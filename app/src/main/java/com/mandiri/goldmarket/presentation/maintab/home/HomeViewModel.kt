package com.mandiri.goldmarket.presentation.maintab.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.remote.request.pocket.Customer
import com.mandiri.goldmarket.data.remote.request.pocket.PocketRequest
import com.mandiri.goldmarket.data.remote.request.pocket.Product
import com.mandiri.goldmarket.data.remote.response.customer.CustomerResponse
import com.mandiri.goldmarket.data.remote.response.pocket.PocketResponse
import com.mandiri.goldmarket.data.remote.response.product.ProductResponse
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRetrofit
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryRetrofit
import com.mandiri.goldmarket.data.repository.product.ProductRetrofitRepository
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(private val customerRepoRetrofit: CustomerRepositoryRetrofit,
                    private val productRepository: ProductRetrofitRepository,
                    private val pocketRetrofit: PocketRepositoryRetrofit
): ViewModel() {

    private var _customerLiveData = MutableLiveData<CustomerResponse>()
    private var _totalBalanceLiveData = MutableLiveData<Int>()
    private var _pocketSelectedLiveData = MutableLiveData<PocketResponse>()
    private var _pocketsLiveData = MutableLiveData<List<PocketResponse>>()
    private var _productLiveData = MutableLiveData<ProductResponse>()
    private val _response = MutableLiveData<EventResult>(EventResult.Idle)

    val response: LiveData<EventResult>
        get() = _response
    val customerLiveData: LiveData<CustomerResponse>
        get() = _customerLiveData
    val totalBalanceLiveData: LiveData<Int>
        get() = _totalBalanceLiveData
    val pocketSelectedLiveData: LiveData<PocketResponse>
        get() = _pocketSelectedLiveData
    val productLiveData: LiveData<ProductResponse>
        get() = _productLiveData

    fun createNewPocketRoom(pocketName: String, custId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            pocketRetrofit.createPocket(PocketRequest(
                pocketName = pocketName,
                pocketQty = 0.0,
                product = Product(1),
                customer = Customer(custId),
                totalAmount = 0)
            )
        }
    }


    private fun homeObservable(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(EventResult.Loading)
            delay(1000)
            val customer = customerRepoRetrofit.findCustomerById()
            val pockets = pocketRetrofit.getAllCustomerPockets()
            val product = productRepository.getProductById(productId)
            val totalBalance = pockets?.map { it.totalAmount }?.sum()
            if (customer == null) {
                Log.d("HomeVM", "homeObserveable: Success")
                _response.postValue(EventResult.ErrorMessage("Can't Retrieve Customer Data"))
                return@launch
            }
            _customerLiveData.postValue(customer)
            Log.d("HomeVM", "homeObservable: $customer")
            _totalBalanceLiveData.postValue(totalBalance ?: 0)
            _pocketsLiveData.postValue(pockets)
            _productLiveData.postValue(product)
            _response.postValue(EventResult.Success(pockets))
        }
    }

    fun getHomeInfo(productId: Int) {
        homeObservable(productId)
    }

    private fun getPocketSelected(idPocket: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val pocketSelected = pocketRetrofit.getPocketById(idPocket)
            _pocketSelectedLiveData.postValue(pocketSelected)
        }
    }

    fun getCurrentPocket(idPocket: String) {
        getPocketSelected(idPocket)
    }

}
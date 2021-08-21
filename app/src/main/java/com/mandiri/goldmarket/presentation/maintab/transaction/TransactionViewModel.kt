package com.mandiri.goldmarket.presentation.maintab.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.remote.request.pocket.Customer
import com.mandiri.goldmarket.data.remote.request.pocket.Product
import com.mandiri.goldmarket.data.remote.request.transaction.Purchase
import com.mandiri.goldmarket.data.remote.request.transaction.PurchaseDetail
import com.mandiri.goldmarket.data.remote.request.transaction.TransactionRequest
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryRoom
import com.mandiri.goldmarket.data.repository.retrofit.PocketRetrofitRepository
import com.mandiri.goldmarket.data.repository.retrofit.TransactionRetrofitRepository
import com.mandiri.goldmarket.data.repository.transaction.TransactionRepositoryRoom
import com.mandiri.goldmarket.presentation.maintab.home.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TransactionViewModel(private val pocketRetrofitRepository: PocketRetrofitRepository,
                           private val transactionRetrofitRepository: TransactionRetrofitRepository
): ViewModel() {

    var transactionType: Int = HomeFragment.TRX_TYPE.toInt()
    var pricePerGram: Int = HomeFragment.TRX_AMOUNT.toInt()
    var pocketId: String = HomeFragment.POCKET_SELECTED
    var transactionGoldGram = MutableLiveData(0.0)

    private val _pocketName = MutableLiveData<String>()
    val pocketName: LiveData<String>
        get() = _pocketName

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double>
        get() = _totalPrice

    private fun observeTotalPrice() {
        _totalPrice.value = transactionGoldGram.value?.toDouble()?.times(pricePerGram)
    }

    fun getTotalPrice() {
        observeTotalPrice()
    }

    fun retrievePocketName() {
        viewModelScope.launch(Dispatchers.IO) {
            val pocket = pocketRetrofitRepository.getPocketById(pocketId)
            _pocketName.postValue(pocket!!.pocketName)
        }
    }

    fun addTransaction(customerId: String, productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRetrofitRepository.performTransaction(TransactionRequest(
                pocketId,
                Purchase(
                    Customer(customerId),
                    listOf(PurchaseDetail(
                        totalPrice.value?.toInt() ?: 0,
                        Product(productId),
                        transactionGoldGram.value ?: 0.0
                    )),
                    transactionType,
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'").format(Date())
                )
            ))
        }

    }

}
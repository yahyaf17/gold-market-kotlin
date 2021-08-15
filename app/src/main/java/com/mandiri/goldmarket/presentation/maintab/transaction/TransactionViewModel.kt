package com.mandiri.goldmarket.presentation.maintab.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryRoom
import com.mandiri.goldmarket.data.repository.transaction.TransactionRepositoryRoom
import com.mandiri.goldmarket.presentation.maintab.home.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(private val pocketRepo: PocketRepositoryRoom,
                           private val transactionRepo: TransactionRepositoryRoom
): ViewModel() {

    var transactionType: String = HomeFragment.TRX_TYPE
    var pricePerGram: Double = HomeFragment.TRX_AMOUNT.toDouble()
    var pocketId: Int = HomeFragment.POCKET_SELECTED.toInt()
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
            val pocket = pocketRepo.findPocketById(pocketId)
            _pocketName.postValue(pocket.name)
        }
    }

    fun addTransaction(customerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionRepo.performPurchase(
                customerId,
                pocketId,
                transactionGoldGram.value ?: 0.0,
                _totalPrice.value ?: 0.0,
                transactionType
            )
        }

    }

}
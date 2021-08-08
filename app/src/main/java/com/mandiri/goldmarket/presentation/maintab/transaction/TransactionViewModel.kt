package com.mandiri.goldmarket.presentation.maintab.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mandiri.goldmarket.data.model.History
import com.mandiri.goldmarket.data.repository.history.HistoryRepositoryImpl
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryImpl
import com.mandiri.goldmarket.presentation.maintab.home.HomeFragment
import java.math.BigDecimal
import java.util.*

class TransactionViewModel(private val pocketRepo: PocketRepositoryImpl, private val historyRepo: HistoryRepositoryImpl): ViewModel() {

    var transactionType: String = HomeFragment.TRX_TYPE
    var pricePerGram: BigDecimal = HomeFragment.TRX_AMOUNT.toBigDecimal()
    var selectedPocket: String = HomeFragment.POCKET_SELECTED
    var transactionGoldGram = MutableLiveData(0.0)

    private val _totalPrice = MutableLiveData(BigDecimal(0))
    val totalPrice: LiveData<BigDecimal>
        get() = _totalPrice

    private fun observeTotalPrice() {
        _totalPrice.value = transactionGoldGram.value?.toBigDecimal()?.times(pricePerGram)
    }

    fun getTotalPrice() {
        observeTotalPrice()
    }

    private fun addPocket() {
        pocketRepo.addPocketTransaction(
            selectedPocket,
            transactionType,
            transactionGoldGram.value ?: 0.0,
            _totalPrice.value ?: BigDecimal(0))
    }

    private fun addHistory() {
        historyRepo.addHistory(
            History(
            pocketRepo.findById(selectedPocket)!!.product,
            transactionType,
            transactionGoldGram.value ?: 0.0,
            totalPrice.value ?: BigDecimal(0),
            selectedPocket,
            Date())
        )
    }

    fun submitTransaction() {
        addPocket()
        addHistory()
    }

}
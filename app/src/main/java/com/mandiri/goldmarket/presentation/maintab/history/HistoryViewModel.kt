package com.mandiri.goldmarket.presentation.maintab.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.model.History
import com.mandiri.goldmarket.data.repository.history.HistoryRepositoryRoom
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryViewModel(val historyRepositoryRoom: HistoryRepositoryRoom): ViewModel() {

    private val _historyLiveData = MutableLiveData<List<History>>()

    private val _response = MutableLiveData<EventResult>(EventResult.Idle)
    val response: LiveData<EventResult>
        get() = _response

    private fun updateHistoryObservable(customerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(EventResult.Loading)
            delay(1000)
            val history = historyRepositoryRoom.getAllHistoryByCustomer(customerId)
            _historyLiveData.postValue(history.get(0).history)
            _response.postValue(EventResult.Success(history.get(0).history))
        }
    }

    fun getHistories(customerId: Int) {
        updateHistoryObservable(customerId)
    }
}
package com.mandiri.goldmarket.presentation.maintab.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.remote.response.history.Content
import com.mandiri.goldmarket.data.repository.retrofit.HistoryRetrofitRepository
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryViewModel(val historyRetrofitRepository: HistoryRetrofitRepository): ViewModel() {

    private val _historyLiveData = MutableLiveData<List<Content>>()

    private val _response = MutableLiveData<EventResult>(EventResult.Idle)
    val response: LiveData<EventResult>
        get() = _response

    private fun updateHistoryObservable() {
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(EventResult.Loading)
            delay(1000)
            val histories = historyRetrofitRepository.getCustomerHistory()
            if (histories == null) {
                _response.postValue(EventResult.Success("No History Data"))
                return@launch
            }
            _historyLiveData.postValue(histories)
            _response.postValue(EventResult.Success(histories))
        }
    }

    fun getHistories() {
        updateHistoryObservable()
    }
}
package com.mandiri.goldmarket.presentation.maintab.history

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mandiri.goldmarket.data.model.History
import com.mandiri.goldmarket.data.repository.history.HistoryRepository
import com.mandiri.goldmarket.utils.EventResult

class HistoryViewModel(val repository: HistoryRepository): ViewModel() {

    private val _historyLiveData = MutableLiveData<List<History>>()

    private val _response = MutableLiveData<EventResult>(EventResult.Idle)
    val response: LiveData<EventResult>
        get() = _response

    private fun getHistoryFromRepo() = repository.findAllHistory()

    private fun updateHistoryObservable() {
        _response.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val history = getHistoryFromRepo()
                _historyLiveData.value = history
                _response.value = EventResult.Success(history)
            } catch (e: Exception) {
                _response.value = EventResult.ErrorMessage("Can't load history")
            }
        }, 1000)

    }

    fun getHistories() {
        updateHistoryObservable()
    }
}
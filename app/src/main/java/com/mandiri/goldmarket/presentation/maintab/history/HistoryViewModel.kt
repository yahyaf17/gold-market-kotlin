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

    private var _historyLiveData = MutableLiveData<EventResult>(EventResult.Idle)

    val historyLiveData: LiveData<EventResult>
        get() = _historyLiveData

    private fun getHistoryFromRepo() = repository.findAllHistory()

    private fun updateHistoryData() {
        _historyLiveData.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val history = getHistoryFromRepo()
                _historyLiveData.value = EventResult.Success(history)
            } catch (e: Exception) {
                _historyLiveData.value = EventResult.ErrorMessage("Can't load history")
            }
        }, 1000)

    }

    fun getHistories() {
        updateHistoryData()
    }
}
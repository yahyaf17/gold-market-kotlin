package com.mandiri.goldmarket.presentation.maintab.pocket

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.data.repository.pocket.PocketRepository
import com.mandiri.goldmarket.utils.EventResult
import java.lang.Exception
import java.math.BigDecimal

class PocketViewModel(val repository: PocketRepository): ViewModel() {
    private var _pocketsLiveData = MutableLiveData<EventResult>(EventResult.Idle)
    private var _pocketCountLiveData = MutableLiveData<EventResult>(EventResult.Idle)

    val pocketsLiveData: LiveData<EventResult>
        get() = _pocketsLiveData

    val pocketCountLiveData: LiveData<EventResult>
        get() = _pocketCountLiveData

    fun getAllPockets() = repository.findAll()

    private fun getAllPocketsInfo() {
        _pocketsLiveData.value = EventResult.Loading
        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val pockets = repository.findAll()
                _pocketsLiveData.value = EventResult.Success(pockets)
            } catch (e: Exception) {
                _pocketsLiveData.value = EventResult.ErrorMessage("Can't Retrieve Pockets data")
            }
        }, 100)
    }

    fun getPockets() {
        getAllPockets()
    }

    private fun getPocket(name: String): Pocket? {
        return repository.findById(name)
    }

    fun updatePocket(exsistingName: String, newName: String): Pocket {
        return repository.editPocket(exsistingName, newName)
    }

    fun getPocketCount(): Int {
        return repository.countPocket()
    }

    fun getPocketTotalBalance(): BigDecimal {
        return repository.totalBalanceOfPocket()
    }

    fun createNewPocket(name: String): Pocket {
        return repository.createNewPocket(name, "Gold")
    }

}
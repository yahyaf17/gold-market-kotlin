package com.mandiri.goldmarket.presentation.maintab.pocket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.data.repository.pocket.PocketRepository
import com.mandiri.goldmarket.utils.EventResult
import java.math.BigDecimal

class PocketViewModel(val repository: PocketRepository): ViewModel() {
    private var _pocketLiveData = MutableLiveData<EventResult>(EventResult.Idle)
    private var _pocketCountLiveData = MutableLiveData<EventResult>(EventResult.Idle)

    val pocketLiveData: LiveData<EventResult>
        get() = _pocketLiveData

    val pocketCountLiveData: LiveData<EventResult>
        get() = _pocketCountLiveData

    private fun getAllPockets() = repository.findAll()

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
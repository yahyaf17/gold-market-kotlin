package com.mandiri.goldmarket.presentation.onboarding.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.goldmarket.data.remote.request.auth.RegisterRequest
import com.mandiri.goldmarket.data.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository
): ViewModel() {

    fun registerCustomerRoom(request: RegisterRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.register(request)
        }
    }

}
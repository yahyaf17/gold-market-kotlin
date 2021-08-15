package com.mandiri.goldmarket.di

import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.data.repository.history.HistoryRepositoryImpl
import com.mandiri.goldmarket.presentation.onboarding.register.RegisterViewModel

class DependencyContainer {
    private val customerRepository = CustomerRepositoryImpl()
    private val historyRepository = HistoryRepositoryImpl()
}
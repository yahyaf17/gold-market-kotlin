package com.mandiri.goldmarket.presentation.maintab.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.databinding.ActivityMainTabBinding
import com.mandiri.goldmarket.presentation.viewmodel.CustomerViewModel
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.CustomSharedPreferences.Username
import com.mandiri.goldmarket.utils.EventResult

class MainTabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainTabBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var customerUsernameFromLogin: String
    private lateinit var customer: Customer
    private lateinit var username: String
    lateinit var sharedPref: SharedPreferences
    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CustomerViewModel(CustomerRepositoryImpl()) as T
        }
    }
    private val viewModel: CustomerViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTabBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = CustomSharedPreferences.customPreference(applicationContext, "Credentials")
        customerUsernameFromLogin = sharedPref.Username.toString()
        customer = viewModel.findCustomerByUsername(customerUsernameFromLogin)!!
        subscriber()

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.navigationBottom.setupWithNavController(navController)
    }

    private fun subscriber() {
        binding.apply {
            val customerObserver: Observer<EventResult> = Observer<EventResult> { events ->
                when(events) {
                    is EventResult.Success -> {
                        username = viewModel.findCustomerByUsername(customerUsernameFromLogin)!!.username
                        viewModel.getCustomer(username)
                    }
                    else -> EventResult.Idle
                }
            }
            viewModel.customerLiveData.observe(this@MainTabActivity, customerObserver)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainTabActivity", "OnDestroy()")
    }
}
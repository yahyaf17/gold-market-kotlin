package com.mandiri.goldmarket.presentation.maintab.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryImpl
import com.mandiri.goldmarket.databinding.FragmentHomeBinding
import com.mandiri.goldmarket.presentation.maintab.main.MainTabActivity
import com.mandiri.goldmarket.presentation.maintab.pocket.NewPocketDialog
import com.mandiri.goldmarket.presentation.maintab.pocket.PocketViewModel
import com.mandiri.goldmarket.presentation.viewmodel.CustomerViewModel
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.CustomSharedPreferences.Username
import com.mandiri.goldmarket.utils.EventResult
import com.mandiri.goldmarket.utils.Formatter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*

class HomeFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentHomeBinding
    private lateinit var customerUsername: String
    private lateinit var customerSelected: Customer
    private lateinit var pocketSelected: Pocket
    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(CustomerRepositoryImpl(), PocketRepositoryImpl()) as T
        }
    }
    private val viewModel: HomeViewModel by viewModels { factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = CustomSharedPreferences.customPreference(requireContext(), "Credentials")
        customerUsername = sharedPref.Username.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCustomer(customerUsername)
        viewModel.getBalanceInfo()
        viewModel.getCurrentPocket("Grasberg")
        subscriber()
        customerSelected = viewModel.findCustomerByUsername(customerUsername)!!
        pocketSelected = viewModel.findPocketByName("Grasberg")!!
        binding.apply {
            btnCreatePocket.setOnClickListener {
                NewPocketDialog.newInstance().show(childFragmentManager, NewPocketDialog.TAG)
            }
        }
    }

    private fun subscriber() {
        binding.apply {
            val customerObserver: Observer<EventResult> = Observer<EventResult> { events ->
                when(events) {
                    is EventResult.Loading -> showProgressBar()
                    is EventResult.Success -> {
                        hideProgressBar()
                        customerSelected = viewModel.findCustomerByUsername(customerUsername)!!
                        textWelcome.text = "Welcome, ${customerSelected.firstName} ${customerSelected.lastName}!"
                    }
                    is EventResult.ErrorMessage -> hideProgressBar()
                    else -> EventResult.Idle
                }
            }
            val pocketObserver: Observer<EventResult> = Observer<EventResult> { events ->
                when(events) {
                    is EventResult.Loading -> showProgressBar()
                    is EventResult.Success -> {
                        hideProgressBar()
                        pocketSelected = viewModel.findPocketByName("Grasberg")!!
                        textPocketQty.text = "Amount: ${pocketSelected.amount} gram"
                        textPocketTitle.text = pocketSelected.name
                        textPocketAmount.text = Formatter.rupiahFormatter(pocketSelected.totalPrice)
                    }
                    is EventResult.ErrorMessage -> hideProgressBar()
                    else -> EventResult.Idle
                }
            }
            val balanceObserver: Observer<EventResult> = Observer<EventResult> { events ->
                when(events) {
                    is EventResult.Loading -> showProgressBar()
                    is EventResult.Success -> {
                        hideProgressBar()
                        textTotalBalance.text = Formatter.rupiahFormatter(viewModel.getPocketTotalBalance())
                    }
                    is EventResult.ErrorMessage -> hideProgressBar()
                    else -> EventResult.Idle
                }
            }
            viewModel.customerLiveData.observe(viewLifecycleOwner, customerObserver)
            viewModel.totalBalanceLiveData.observe(viewLifecycleOwner, balanceObserver)
            viewModel.pocketSelectedLiveData.observe(viewLifecycleOwner, pocketObserver)
        }
    }

    private fun showProgressBar() {
        home_progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        home_progressbar.visibility = View.GONE
    }
}
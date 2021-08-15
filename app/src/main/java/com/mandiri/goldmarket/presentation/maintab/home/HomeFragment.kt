package com.mandiri.goldmarket.presentation.maintab.home

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.db.AppDatabase
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRoom
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryRoom
import com.mandiri.goldmarket.data.repository.product.ProductRepositoryImpl
import com.mandiri.goldmarket.databinding.FragmentHomeBinding
import com.mandiri.goldmarket.presentation.maintab.pocket.NewPocketDialog
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.CustomSharedPreferences.CustomerId
import com.mandiri.goldmarket.utils.EventResult
import kotlin.properties.Delegates

class HomeFragment : Fragment(), HomePocketAdapter.OnClickItem {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentHomeBinding
    private var customerId by Delegates.notNull<Int>()
    private lateinit var pockets: List<Pocket>
    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val db = this@HomeFragment.context?.let { AppDatabase.getDatabase(it) }
            return HomeViewModel(
                ProductRepositoryImpl(),
                CustomerRepositoryRoom(db!!),
                PocketRepositoryRoom(db)
            ) as T
        }
    }
    private val viewModel: HomeViewModel by viewModels { factory }
    private lateinit var homePocketAdapter: HomePocketAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = CustomSharedPreferences.credentialsPref(requireContext())
        customerId = sharedPref.CustomerId
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriber()
        viewModel.getHomeInfo(customerId, 1)
        binding.apply {

            lifecycleOwner = this@HomeFragment
            homeVM = viewModel

            btnCreatePocket.setOnClickListener {
                NewPocketDialog.newInstance().show(childFragmentManager, NewPocketDialog.TAG)
            }

            rvPocket.apply {
                layoutManager = LinearLayoutManager(this@HomeFragment.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = homePocketAdapter
            }

            btnBuy.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_transactionFragment,
                    bundleOf(
                        TRX_TYPE to "Buy",
                        TRX_AMOUNT to viewModel.productLiveData.value!!.priceBuy,
                        POCKET_SELECTED to viewModel.pocketSelectedLiveData.value?.pocketId
                    ))
            }

            btnSell.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_transactionFragment,
                    bundleOf(
                        TRX_TYPE to "Sell",
                        TRX_AMOUNT to viewModel.productLiveData.value!!.priceSell,
                        POCKET_SELECTED to viewModel.pocketSelectedLiveData.value?.pocketId
                    ))
            }

            btnSwitchProduct.setOnClickListener {
                Toast.makeText(this@HomeFragment.context,
                    "Other product still in development",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun subscriber() {
        homePocketAdapter = HomePocketAdapter(this@HomeFragment)
        viewModel.response.observe(this.viewLifecycleOwner) {
            when (it) {
                is EventResult.Loading -> showProgressBar()
                is EventResult.Success -> {
                    hideProgressBar()
                    pockets = (it.data as? List<Pocket>)!!
                    if (pockets.isEmpty()) {
                        showEmptyDataHandling()
                        return@observe
                    }
                    hideEmptyDataHandling()
                    homePocketAdapter.updateData(pockets)
                }
                is EventResult.ErrorMessage -> hideProgressBar()
                else -> EventResult.Idle
            }
        }
    }

    private fun showProgressBar() {
        binding.homeProgressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.homeProgressbar.visibility = View.GONE
    }

    override fun onChangePocket(position: Int) {
        viewModel.getCurrentPocket(pockets[position].pocketId)
    }

    private fun showEmptyDataHandling() {
        binding.pokcetEmptyContainer.visibility = View.VISIBLE
    }

    private fun hideEmptyDataHandling() {
        binding.pokcetEmptyContainer.visibility = View.GONE
    }

    companion object {
        const val TRX_TYPE = "TRX_TYPE"
        const val TRX_AMOUNT = "1"
        const val POCKET_SELECTED = "0"
    }

}
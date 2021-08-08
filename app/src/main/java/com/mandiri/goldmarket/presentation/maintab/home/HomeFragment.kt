package com.mandiri.goldmarket.presentation.maintab.home

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.model.Pocket
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryImpl
import com.mandiri.goldmarket.data.repository.product.ProductRepositoryImpl
import com.mandiri.goldmarket.databinding.FragmentHomeBinding
import com.mandiri.goldmarket.presentation.maintab.pocket.NewPocketDialog
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.CustomSharedPreferences.Username
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*

class HomeFragment : Fragment(), HomePocketAdapter.OnClickItem {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: FragmentHomeBinding
    private lateinit var customerUsername: String
    private lateinit var pockets: List<Pocket>
    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(
                CustomerRepositoryImpl(),
                PocketRepositoryImpl(),
                ProductRepositoryImpl()) as T
        }
    }
    private val viewModel: HomeViewModel by viewModels { factory }
    private lateinit var homePocketAdapter: HomePocketAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = CustomSharedPreferences.customPreference(requireContext(), "Credentials")
        customerUsername = sharedPref.Username.toString()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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
        subscriber()
        viewModel.getHomeInfo(customerUsername, "Grasberg", "1")
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
                        TRX_AMOUNT to viewModel.productLiveData.value?.priceBuy?.toDouble(),
                        POCKET_SELECTED to viewModel.pocketSelectedLiveData.value?.name
                    ))
            }

            btnSell.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_transactionFragment,
                    bundleOf(
                        TRX_TYPE to "Sell",
                        TRX_AMOUNT to viewModel.productLiveData.value?.priceSell?.toDouble(),
                        POCKET_SELECTED to viewModel.pocketSelectedLiveData.value?.name
                    ))
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
                    pockets = it.data as List<Pocket>
                    homePocketAdapter.updateData(pockets)
                }
                is EventResult.ErrorMessage -> hideProgressBar()
                else -> EventResult.Idle
            }
        }
    }

    private fun showProgressBar() {
        home_progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        home_progressbar.visibility = View.GONE
    }

    override fun onChangePocket(position: Int) {
        viewModel.getCurrentPocket(pockets[position].name)
    }

    companion object {
        const val TRX_TYPE = "TRX_TYPE"
        const val TRX_AMOUNT = "0"
        const val POCKET_SELECTED = "POCKET"
    }

}
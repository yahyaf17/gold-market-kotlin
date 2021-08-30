package com.mandiri.goldmarket.presentation.maintab.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.remote.response.pocket.PocketResponse
import com.mandiri.goldmarket.databinding.FragmentHomeBinding
import com.mandiri.goldmarket.presentation.ViewModelFactoryBase
import com.mandiri.goldmarket.presentation.maintab.home.pocket.EditPocketDialog
import com.mandiri.goldmarket.presentation.maintab.home.pocket.NewPocketDialog
import com.mandiri.goldmarket.presentation.maintab.home.product.ChangeProductDialog
import com.mandiri.goldmarket.utils.EventResult
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment(), HomePocketAdapter.OnClickItem {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var pockets: List<PocketResponse>

    @Inject
    lateinit var viewModel: HomeViewModel
    private lateinit var homePocketAdapter: HomePocketAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onResume() {
        super.onResume()
        binding.apply {
            btnSell.isEnabled = false
            btnBuy.isEnabled = false
            alertPickPocket.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriber()
        initViewModel()
        viewModel.getHomeInfo(1)
        binding.apply {

            // active when current pocket is showing
            btnSell.isEnabled = false
            btnBuy.isEnabled = false

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
                        TRX_TYPE to 1, //1 for Buy
                        TRX_AMOUNT to viewModel.productLiveData.value!!.productPriceBuy,
                        POCKET_SELECTED to viewModel.pocketSelectedLiveData.value?.id,
                        PRODUCT_ID to viewModel.productLiveData.value!!.id
                    ))
            }

            btnSell.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_transactionFragment,
                    bundleOf(
                        TRX_TYPE to 0, // 0 for Sell
                        TRX_AMOUNT to viewModel.productLiveData.value!!.productPriceSell,
                        POCKET_SELECTED to viewModel.pocketSelectedLiveData.value?.id,
                        PRODUCT_ID to viewModel.productLiveData.value!!.id
                    ))
            }

            btnSwitchProduct.setOnClickListener {
                ChangeProductDialog.newInstance().show(childFragmentManager, ChangeProductDialog.TAG)
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactoryBase {
            viewModel
        }).get(HomeViewModel::class.java)
    }


    private fun subscriber() {
        homePocketAdapter = HomePocketAdapter(this@HomeFragment)
        viewModel.response.observe(this.viewLifecycleOwner) {
            when (it) {
                is EventResult.Loading -> showProgressBar()
                is EventResult.Success -> {
                    hideProgressBar()
                    pockets = it.data as List<PocketResponse>
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
        viewModel.deleteResponse.observe(this.viewLifecycleOwner) {
            when(it) {
                is EventResult.Loading -> showProgressBar()
                is EventResult.Success -> {
                    Toast.makeText(this.requireContext(), "Delete Success", Toast.LENGTH_SHORT)
                        .show()
                    hideProgressBar()
                }
                is EventResult.ErrorMessage -> {
                    Toast.makeText(this.requireContext(), "Delete Failed", Toast.LENGTH_SHORT)
                        .show()
                    hideProgressBar()
                }
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
        binding.apply {
            btnBuy.isEnabled = true
            btnSell.isEnabled = true
            alertPickPocket.visibility = View.GONE
        }
        viewModel.getCurrentPocket(pockets[position].id)
    }

    override fun onEditPocket(position: Int) {
        viewModel.getCurrentPocket(pockets[position].id)
        EditPocketDialog.newInstance(pockets[position].id)
            .show(childFragmentManager, EditPocketDialog.TAG)
    }

    override fun onDeletePocket(position: Int) {
        AlertDialog.Builder(this.context).setTitle("Delete ${pockets[position].pocketName} pocket?")
            .setMessage("Are you sure want to delete this pocket?")
            .setPositiveButton("Proceed", DialogInterface.OnClickListener { dialog, _ ->
                if (pockets[position].pocketQty > 0.00) {
                    dialog.cancel()
                    Toast.makeText(this.requireContext(), "Pocket Must be empty", Toast.LENGTH_SHORT)
                        .show()
                    return@OnClickListener
                }
                viewModel.deletePocket(pockets[position].id)
                viewModel.getHomeInfo(pockets[position].product.id)
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
            .create().show()
    }

    private fun showEmptyDataHandling() {
        binding.pokcetEmptyContainer.visibility = View.VISIBLE
    }

    private fun hideEmptyDataHandling() {
        binding.pokcetEmptyContainer.visibility = View.GONE
    }

    companion object {
        const val TRX_TYPE = "2"
        const val TRX_AMOUNT = "1"
        const val POCKET_SELECTED = "0"
        const val PRODUCT_ID = "productId"
    }

}
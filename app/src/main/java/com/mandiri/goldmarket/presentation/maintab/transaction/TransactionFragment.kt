package com.mandiri.goldmarket.presentation.maintab.transaction

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.data.repository.history.HistoryRepositoryImpl
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryImpl
import com.mandiri.goldmarket.data.repository.product.ProductRepositoryImpl
import com.mandiri.goldmarket.databinding.FragmentTransactionBinding
import com.mandiri.goldmarket.presentation.maintab.home.HomeFragment
import com.mandiri.goldmarket.presentation.maintab.home.HomeViewModel
import com.mandiri.goldmarket.presentation.maintab.main.MainTabActivity
import com.mandiri.goldmarket.utils.Formatter
import kotlinx.android.synthetic.main.fragment_transaction.*
import java.math.BigDecimal

class TransactionFragment : Fragment() {

    private lateinit var binding: FragmentTransactionBinding
    private lateinit var type: String
    private lateinit var price: BigDecimal
    private lateinit var selectedPocket: String
    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TransactionViewModel(
                PocketRepositoryImpl(),
                HistoryRepositoryImpl()
            ) as T
        }
    }
    private val viewModel: TransactionViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            type = getString(HomeFragment.TRX_TYPE).toString()
            price = getDouble(HomeFragment.TRX_AMOUNT).toBigDecimal()
            selectedPocket = getString(HomeFragment.POCKET_SELECTED).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTransactionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriber()
        viewModel.transactionType = type
        viewModel.pricePerGram = price
        viewModel.selectedPocket = selectedPocket
        viewModel.getTotalPrice()
        binding.apply {
            lifecycleOwner = this@TransactionFragment
            trxVM = viewModel
            inputInGram.addTextChangedListener(object :TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    validate()
                }

                override fun afterTextChanged(s: Editable?) {
                    validate()
                }
            })

            btnSubmitTrx.setOnClickListener {
                viewModel.submitTransaction()
                findNavController().navigate(R.id.action_transactionFragment_to_homeFragment)
            }
        }
    }

    private fun subscriber() {
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.trxTotalPrice.text = Formatter.rupiahFormatter(it)
        }
    }

    fun validate() {
        if (inputInGram.text.isNullOrBlank() && inputInGram.equals(0.0)) btn_submit_trx.isEnabled = false
        viewModel.getTotalPrice()
    }


    companion object {

        @JvmStatic
        fun newInstance() = TransactionFragment()
    }
}
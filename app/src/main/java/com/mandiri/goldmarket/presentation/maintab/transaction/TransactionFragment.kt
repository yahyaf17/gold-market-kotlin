package com.mandiri.goldmarket.presentation.maintab.transaction

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.databinding.FragmentTransactionBinding
import com.mandiri.goldmarket.presentation.ViewModelFactoryBase
import com.mandiri.goldmarket.presentation.maintab.home.HomeFragment
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.Formatter
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.properties.Delegates

class TransactionFragment : DaggerFragment() {

    private lateinit var binding: FragmentTransactionBinding
    private lateinit var customerId: String
    private var type by Delegates.notNull<Int>()
    private var price by Delegates.notNull<Int>()
    private var productId by Delegates.notNull<Int>()
    private lateinit var selectedPocket: String

    @Inject
    lateinit var viewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            type = getInt(HomeFragment.TRX_TYPE)
            price = getInt(HomeFragment.TRX_AMOUNT)
            selectedPocket = getString(HomeFragment.POCKET_SELECTED).toString()
            productId = getInt(HomeFragment.PRODUCT_ID)
        }
        val initSharedPreference = requireContext().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        val sharedPref = CustomSharedPreferences(initSharedPreference)
        customerId = sharedPref.retrieveValue(CustomSharedPreferences.Key.CUSTOMER_ID).toString()
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
        initViewModel()
        viewModel.transactionType = type
        viewModel.pricePerGram = price
        viewModel.pocketId = selectedPocket
        viewModel.getTotalPrice()
        viewModel.retrievePocketName()
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
                viewModel.addTransaction(customerId, productId)
                findNavController().navigate(R.id.action_transactionFragment_to_homeFragment)
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactoryBase {
            viewModel
        }).get(TransactionViewModel::class.java)
    }


    private fun subscriber() {
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.trxTotalPrice.text = Formatter.rupiahFormatter(it)
        }
        viewModel.pocketName.observe(viewLifecycleOwner) {
            binding.pocketNameDetails.text = it
        }
    }

    fun validate() {
        binding.apply {
            if (inputInGram.text.isNullOrBlank() && inputInGram.equals(0.0)) btnSubmitTrx.isEnabled = false
            viewModel.getTotalPrice()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = TransactionFragment()
    }
}
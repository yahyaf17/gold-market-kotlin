package com.mandiri.goldmarket.presentation.maintab.transaction

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.db.AppDatabase
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryRoom
import com.mandiri.goldmarket.data.repository.transaction.TransactionRepositoryRoom
import com.mandiri.goldmarket.databinding.FragmentTransactionBinding
import com.mandiri.goldmarket.presentation.maintab.home.HomeFragment
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.CustomSharedPreferences.CustomerId
import com.mandiri.goldmarket.utils.Formatter
import kotlin.properties.Delegates

class TransactionFragment : Fragment() {

    private lateinit var binding: FragmentTransactionBinding
    private lateinit var sharedPref: SharedPreferences
    private var customerId by Delegates.notNull<Int>()
    private lateinit var type: String
    private var price by Delegates.notNull<Double>()
    private var selectedPocket by Delegates.notNull<Int>()
    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val db = this@TransactionFragment.context?.let { AppDatabase.getDatabase(it) }
            return TransactionViewModel(
                PocketRepositoryRoom(db!!),
                TransactionRepositoryRoom(db)
            ) as T
        }
    }
    private val viewModel: TransactionViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            type = getString(HomeFragment.TRX_TYPE).toString()
            price = getDouble(HomeFragment.TRX_AMOUNT)
            selectedPocket = getInt(HomeFragment.POCKET_SELECTED)
        }
        sharedPref = CustomSharedPreferences.credentialsPref(requireContext())
        customerId = sharedPref.CustomerId
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
                viewModel.addTransaction(customerId)
                findNavController().navigate(R.id.action_transactionFragment_to_homeFragment)
            }
        }
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
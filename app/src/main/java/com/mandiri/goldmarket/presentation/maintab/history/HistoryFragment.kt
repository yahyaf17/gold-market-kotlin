package com.mandiri.goldmarket.presentation.maintab.history

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mandiri.goldmarket.data.remote.RetrofitInstance
import com.mandiri.goldmarket.data.remote.response.history.Content
import com.mandiri.goldmarket.data.repository.retrofit.HistoryRetrofitRepository
import com.mandiri.goldmarket.databinding.FragmentHistoryBinding
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.EventResult
import kotlin.properties.Delegates

class HistoryFragment : Fragment() {

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var binding: FragmentHistoryBinding
    private var customerId by Delegates.notNull<Int>()
    private val factory = object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val sharedPreferences = CustomSharedPreferences(requireContext())
            val historyApi = RetrofitInstance(sharedPreferences).historyApi
            return HistoryViewModel(HistoryRetrofitRepository(historyApi, sharedPreferences)) as T
        }
    }
    private val viewModel: HistoryViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = CustomSharedPreferences(requireContext())
        customerId = sharedPref.retrieveInt(CustomSharedPreferences.Key.USER_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriber()
        viewModel.getHistories()
        binding.apply {
            lifecycleOwner = this@HistoryFragment
            rvHistory.apply {
                layoutManager = LinearLayoutManager(this@HistoryFragment.context)
                adapter = historyAdapter
            }
        }
    }

    private fun subscriber() {
        historyAdapter = HistoryAdapter()
        viewModel.response.observe(this.viewLifecycleOwner) {
            when (it) {
                is EventResult.Loading -> showProgressBar()
                is EventResult.Success -> {
                    hideProgressBar()
                    val histories = it.data as List<Content>
                    historyAdapter.updateData(it.data)
                    if (histories.isNotEmpty()) {
                        hideEmptyDataHandling()
                        return@observe
                    }
                    showEmptyDataHandling()
                }
                is EventResult.ErrorMessage -> {
                    hideProgressBar()
                    showEmptyDataHandling()
                }
                else -> EventResult.Idle
            }
        }
    }

    private fun showProgressBar() {
        binding.historyLoading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.historyLoading.visibility = View.GONE
    }

    private fun showEmptyDataHandling() {
        binding.emptyHandlingContainer.visibility = View.VISIBLE
    }

    private fun hideEmptyDataHandling() {
        binding.emptyHandlingContainer.visibility = View.GONE
    }


    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}
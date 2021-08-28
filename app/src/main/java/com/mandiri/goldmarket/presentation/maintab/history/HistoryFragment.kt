package com.mandiri.goldmarket.presentation.maintab.history

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mandiri.goldmarket.data.remote.response.history.Content
import com.mandiri.goldmarket.databinding.FragmentHistoryBinding
import com.mandiri.goldmarket.presentation.ViewModelFactoryBase
import com.mandiri.goldmarket.utils.EventResult
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HistoryFragment : DaggerFragment() {

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var binding: FragmentHistoryBinding
    @Inject
    lateinit var viewModel: HistoryViewModel

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
        initViewModel()
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

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactoryBase {
            viewModel
        }).get(HistoryViewModel::class.java)
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
package com.mandiri.goldmarket.presentation.maintab.history

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mandiri.goldmarket.data.model.History
import com.mandiri.goldmarket.data.repository.history.HistoryRepositoryImpl
import com.mandiri.goldmarket.databinding.FragmentHistoryBinding
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var binding: FragmentHistoryBinding
    private val factory = object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HistoryViewModel(HistoryRepositoryImpl()) as T
        }
    }
    private val viewModel: HistoryViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHistories()
        subscriber()
        binding.apply {
            rvHistory.apply {
                layoutManager = LinearLayoutManager(this@HistoryFragment.context)
                adapter = historyAdapter
            }
        }
    }

    private fun subscriber() {
        binding.apply {
            historyAdapter = HistoryAdapter()
            val historyObserver: Observer<EventResult> = Observer<EventResult> { events ->
                when(events) {
                    is EventResult.Loading -> {
                        hideEmptyDataHandling()
                        showProgressBar()
                    }
                    is EventResult.Success -> {
                        val histories = events.data as List<History>
                        historyAdapter.updateData(events.data)
                        hideProgressBar()
                        if (histories.isNotEmpty()) {
                            hideEmptyDataHandling()
                        } else {
                           showEmptyDataHandling()
                        }
                    }
                    is EventResult.ErrorMessage ->  {
                        hideProgressBar()
                    }
                    else -> EventResult.Idle
                }
            }
            viewModel.historyLiveData.observe(viewLifecycleOwner, historyObserver)
        }
    }

    private fun showProgressBar() {
        history_loading.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        history_loading.visibility = View.GONE
    }

    private fun showEmptyDataHandling() {
        empty_handling_container.visibility = View.VISIBLE
    }

    private fun hideEmptyDataHandling() {
        empty_handling_container.visibility = View.GONE
    }


    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}
package com.mandiri.goldmarket.maintab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HistoryFragment()
    }
}
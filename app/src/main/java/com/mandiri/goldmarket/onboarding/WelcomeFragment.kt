package com.mandiri.goldmarket.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnGetStarted.setOnClickListener {
                findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WelcomeFragment()
    }
}
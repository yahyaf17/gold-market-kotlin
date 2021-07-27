package com.mandiri.goldmarket.maintab

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSignOut.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_onboardingActivity)
                (activity as? MainTabActivity)?.finish()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
package com.mandiri.goldmarket.presentation.maintab.profile

import android.content.SharedPreferences
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
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryImpl
import com.mandiri.goldmarket.databinding.FragmentProfileBinding
import com.mandiri.goldmarket.presentation.maintab.main.MainTabActivity
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.CustomSharedPreferences.Username
import com.mandiri.goldmarket.utils.EventResult
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var customerUsername: String
    private lateinit var customerSelected: Customer
    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProfileViewModel(CustomerRepositoryImpl(), PocketRepositoryImpl()) as T
        }
    }
    private val viewModel: ProfileViewModel by viewModels { factory }
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = CustomSharedPreferences.customPreference(requireContext(), "Credentials")
        customerUsername = sharedPref.Username.toString()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriber()
        viewModel.getProfileInfo(customerUsername)

        binding.apply {
            lifecycleOwner = this@ProfileFragment
            profileVM = viewModel
            btnSignOut.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_onboardingActivity)
                (activity as? MainTabActivity)?.finish()
            }

            btnEditProfile.setOnClickListener {
                EditProfileDialog.newInstance(customerUsername).show(childFragmentManager, EditProfileDialog.TAG)
            }

        }
    }

    private fun subscriber() {
        viewModel.response.observe(this.viewLifecycleOwner) {
            when (it) {
                is EventResult.Loading -> showProgressBar()
                is EventResult.Success -> hideProgressBar()
                is EventResult.ErrorMessage -> hideProgressBar()
                else -> EventResult.Idle
            }
        }
    }

    private fun showProgressBar() {
        profile_progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        profile_progressbar.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
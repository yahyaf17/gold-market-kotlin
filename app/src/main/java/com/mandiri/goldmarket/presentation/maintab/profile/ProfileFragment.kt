package com.mandiri.goldmarket.presentation.maintab.profile

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.remote.RetrofitInstance
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRetrofit
import com.mandiri.goldmarket.data.repository.pocket.PocketRepositoryRetrofit
import com.mandiri.goldmarket.databinding.FragmentProfileBinding
import com.mandiri.goldmarket.presentation.maintab.main.MainTabActivity
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.EventResult
import kotlin.properties.Delegates

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var customerId by Delegates.notNull<Int>()
    private lateinit var custIdString: String
    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val sharedPreferences = CustomSharedPreferences(requireContext())
            val customerApi = RetrofitInstance(sharedPreferences).customerApi
            val pocketApi = RetrofitInstance(sharedPreferences).pocketApi
            return ProfileViewModel(
                CustomerRepositoryRetrofit(customerApi, sharedPreferences),
                PocketRepositoryRetrofit(pocketApi, sharedPreferences)
            ) as T
        }
    }
    private val viewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = CustomSharedPreferences(requireContext())
        customerId = sharedPref.retrieveInt(CustomSharedPreferences.Key.USER_ID)
        custIdString = sharedPref.retrieveString(CustomSharedPreferences.Key.CUSTOMER_ID).toString()
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
        viewModel.getProfileInfo(customerId)

        binding.apply {
            lifecycleOwner = this@ProfileFragment
            profileVM = viewModel
            btnSignOut.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_onboardingActivity)
                (activity as? MainTabActivity)?.finish()
                CustomSharedPreferences(requireContext()).clearAll()
            }

            btnEditProfile.setOnClickListener {
                EditProfileDialog.newInstance(custIdString).show(childFragmentManager, EditProfileDialog.TAG)
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
        binding.profileProgressbar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.profileProgressbar.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
package com.mandiri.goldmarket.presentation.maintab.profile

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.databinding.FragmentProfileBinding
import com.mandiri.goldmarket.presentation.ViewModelFactoryBase
import com.mandiri.goldmarket.presentation.maintab.main.MainTabActivity
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.EventResult
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment : DaggerFragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var custIdString: String
    @Inject
    lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        initViewModel()
        subscriber()
        viewModel.getProfileInfo()

        val sharedPref = requireContext().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        binding.apply {
            lifecycleOwner = this@ProfileFragment
            profileVM = viewModel
            btnSignOut.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_onboardingActivity)
                (activity as? MainTabActivity)?.finish()
                CustomSharedPreferences(sharedPref).clearAll()
            }

            btnEditProfile.setOnClickListener {
                custIdString = CustomSharedPreferences(sharedPref)
                    .retrieveValue(CustomSharedPreferences.Key.CUSTOMER_ID).toString()
                EditProfileDialog.newInstance(custIdString).show(childFragmentManager, EditProfileDialog.TAG)
            }

        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactoryBase {
            viewModel
        }).get(ProfileViewModel::class.java)
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
package com.mandiri.goldmarket.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.databinding.FragmentRegisterBinding
import com.mandiri.goldmarket.utils.ButtonUtils
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    var toggleOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.also { bind ->
            bind.btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

            bind.textToLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }

            bind.showPassRegister.setOnClickListener {
                this.toggleOn = !this.toggleOn
                ButtonUtils.showPasswordUtils(this.toggleOn, textRegisterPassword, showPassRegister)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}
package com.mandiri.goldmarket.presentation.onboarding.register

import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.databinding.FragmentRegisterBinding
import com.mandiri.goldmarket.utils.ButtonUtils
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RegisterViewModel(CustomerRepositoryImpl()) as T
        }
    }
    private val viewModel: RegisterViewModel by viewModels { factory }
    private var toggleOn: Boolean = false


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
                try {
                    performRegister()
                    showSuccessRegisterDialog()
                } catch (e: Exception) {
                    AlertDialog.Builder(this.context).setTitle("Register Failed")
                        .setMessage("Email must be valid and password length minimum 5 characters")
                        .create().show()
                }
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

    private fun performRegister() {
        if (textRegisterPassword.text.length < 5)  {
            Toast.makeText(this.context, "Minimum password length 5 characters", Toast.LENGTH_SHORT).show()
            throw Exception()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail.text.toString()).matches()) {
            Toast.makeText(this.context, "Email not valid", Toast.LENGTH_SHORT).show()
            throw Exception()
        } else {
            viewModel.addCustomer(Customer(
                textFirstName.text.toString(),
                textLastName.text.toString(),
                textEmail.text.toString(),
                textUsernameRegister.text.toString(),
                textRegisterPassword.text.toString(),
            ))
        }
    }

    private fun showSuccessRegisterDialog() {
        AlertDialog.Builder(this.context).apply {
            setTitle("Success Register")
            setMessage("Register successful, please login!")
            setPositiveButton("To Login") { dialog, which ->
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }.create().show()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}
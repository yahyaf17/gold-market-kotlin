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
import com.mandiri.goldmarket.data.db.AppDatabase
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRoom
import com.mandiri.goldmarket.databinding.FragmentRegisterBinding
import com.mandiri.goldmarket.utils.ButtonUtils


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val db = this@RegisterFragment.context?.let { AppDatabase.getDatabase(it) }
            return RegisterViewModel(CustomerRepositoryRoom(db!!)) as T
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
                ButtonUtils.showPasswordUtils(this.toggleOn, bind.textRegisterPassword, bind.showPassRegister)
            }
        }
    }

    private fun performRegister() {
        binding.apply {
            if (textRegisterPassword.text.length < 5)  {
                Toast.makeText(this@RegisterFragment.context, "Minimum password length 5 characters", Toast.LENGTH_SHORT).show()
                throw Exception()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail.text.toString()).matches()) {
                Toast.makeText(this@RegisterFragment.context, "Email not valid", Toast.LENGTH_SHORT).show()
                throw Exception()
            } else {
                viewModel.registerCustomerRoom(Customer(
                    firstName = textFirstName.text.toString(),
                    lastName = textLastName.text.toString(),
                    email = textEmail.text.toString(),
                    username = textUsernameRegister.text.toString(),
                    password = textRegisterPassword.text.toString(),
                ))
            }
        }
    }

    private fun showSuccessRegisterDialog() {
        AlertDialog.Builder(this.context).apply {
            setTitle("Success Register")
            setMessage("Register successful, please login!")
            setPositiveButton("To Login") { _, _ ->
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }.create().show()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}
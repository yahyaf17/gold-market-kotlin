package com.mandiri.goldmarket.presentation.onboarding.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.data.db.AppDatabase
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryRoom
import com.mandiri.goldmarket.databinding.FragmentLoginBinding
import com.mandiri.goldmarket.presentation.maintab.main.MainTabActivity
import com.mandiri.goldmarket.presentation.onboarding.onboard.OnboardingActivity
import com.mandiri.goldmarket.utils.ButtonUtils
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.CustomSharedPreferences.CustomerId
import com.mandiri.goldmarket.utils.EventResult
import kotlin.properties.Delegates

class LoginFragment() : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val db = this@LoginFragment.context?.let { AppDatabase.getDatabase(it) }
            return LoginViewModel(CustomerRepositoryRoom(db!!)) as T
        }
    }
    private val viewModel: LoginViewModel by viewModels { factory }
    private var toggleOn by Delegates.notNull<Boolean>()
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = CustomSharedPreferences.credentialsPref(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.toggleOn = false
        super.onViewCreated(view, savedInstanceState)
        subscribe()

        binding.apply {

            lifecycleOwner = this@LoginFragment
            loginVM = viewModel

            btnLogin.isEnabled = false
            val textEdits = listOf(textLoginUsername, textLoginPassword)
            disableLoginBtn(textEdits)
            btnLogin.setOnClickListener {
                viewModel.loginCustomerRoom(
                    textLoginUsername.text.toString() ?: " ",
                    textLoginPassword.text.toString() ?: " "
                )
            }

            showPassLogin.setOnClickListener {
               togglePassword()
            }

            textToRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun disableLoginBtn(list: List<EditText>) {
        binding.apply {
            for (l in list) {
                l.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        btnLogin.isEnabled =
                            (textLoginUsername.length() > 0 && textLoginPassword.length() >= 5)
                    }
                    override fun afterTextChanged(s: Editable?) {
                    }
                })
            }
        }
    }

    private fun togglePassword() {
        this.toggleOn = !this.toggleOn
        binding.apply {
            ButtonUtils.showPasswordUtils(this@LoginFragment.toggleOn, textLoginPassword, showPassLogin)
        }
    }

    private fun moveToHome(customer: Customer?) {
        val thisActivity = (activity as OnboardingActivity)
        val intent = Intent(thisActivity, MainTabActivity::class.java)
        customer?.let {
//            sharedPref.Username = binding.textLoginUsername.text.toString()
            sharedPref.CustomerId = customer?.customerId!!
            startActivity(intent)
            thisActivity.finish()
        } ?: Toast.makeText(this@LoginFragment.context, "Wrong password or account not registered", Toast.LENGTH_LONG).show()
        Log.d("LoginFragment", "onViewCreated cust: ${customer}")
    }

    private fun subscribe() {
        binding.apply {
            viewModel.response.observe(this@LoginFragment.viewLifecycleOwner) {
                when(it) {
                    is EventResult.Loading -> showProgressBar()
                    is EventResult.ErrorMessage -> {
                        hideProgressBar()
                        moveToHome(null)
                    }
                    is EventResult.Success -> {
                        hideProgressBar()
                        moveToHome(it.data as Customer)
                    }
                    else -> hideProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.loginProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.loginProgressBar.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}
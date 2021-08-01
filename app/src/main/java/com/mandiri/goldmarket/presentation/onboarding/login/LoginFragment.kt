package com.mandiri.goldmarket.presentation.onboarding.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.mandiri.goldmarket.data.model.Customer
import com.mandiri.goldmarket.data.repository.customer.CustomerRepositoryImpl
import com.mandiri.goldmarket.databinding.FragmentLoginBinding
import com.mandiri.goldmarket.presentation.maintab.main.MainTabActivity
import com.mandiri.goldmarket.presentation.onboarding.onboard.OnboardingActivity
import com.mandiri.goldmarket.presentation.viewmodel.CustomerViewModel
import com.mandiri.goldmarket.utils.ButtonUtils
import com.mandiri.goldmarket.utils.CustomSharedPreferences
import com.mandiri.goldmarket.utils.CustomSharedPreferences.Username
import kotlinx.android.synthetic.main.fragment_login.*
import kotlin.properties.Delegates

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private  val factory =  object: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CustomerViewModel(CustomerRepositoryImpl()) as T
        }
    }
    private val viewModel: CustomerViewModel by viewModels { factory }
    var toggleOn by Delegates.notNull<Boolean>()
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = CustomSharedPreferences.customPreference(requireContext(), "CREDENTIALS")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        this.toggleOn = false
        btnLogin.isEnabled = false
        val textEdits = listOf(textLoginUsername, textLoginPassword)
        disableLoginBtn(textEdits)

        super.onViewCreated(view, savedInstanceState)

        binding.also {

            it.btnLogin.setOnClickListener {

                val customerSelected: Customer? = viewModel.loginCustomer(
                    textLoginUsername.text.toString(),
                    textLoginPassword.text.toString()
                )
                customerSelected?.let {
                    val thisActivity = (activity as OnboardingActivity)
                    val intent = Intent(thisActivity, MainTabActivity::class.java)
                    intent.putExtra(CUSTOMER_USERNAME, textLoginUsername.text.toString())
                    sharedPref.Username = textLoginUsername.text.toString()
                    startActivity(intent)
                    thisActivity.finish()
                } ?: Toast.makeText(this.context, "Wrong password or account not registered", Toast.LENGTH_LONG).show()
            }

            it.showPassLogin.setOnClickListener {
               togglePassword()
            }

            it.textToRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun disableLoginBtn(list: List<EditText>) {
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

    private fun togglePassword() {
        this.toggleOn = !this.toggleOn
        ButtonUtils.showPasswordUtils(this.toggleOn, textLoginPassword, showPassLogin)
    }

    private fun showProgressBar() {
//        history_loading.visibility = View.VISIBLE
    }

    companion object {
        const val CUSTOMER_USERNAME = "username"
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}
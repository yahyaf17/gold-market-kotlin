package com.mandiri.goldmarket.onboarding

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.databinding.FragmentLoginBinding
import com.mandiri.goldmarket.maintab.MainTabActivity
import com.mandiri.goldmarket.utils.ButtonUtils
import kotlinx.android.synthetic.main.fragment_login.*
import kotlin.properties.Delegates

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    var toggleOn by Delegates.notNull<Boolean>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                if (textLoginUsername.text.toString() == "tes" && textLoginPassword.text.toString() == "enigma") {
                    val thisActivity = (activity as OnboardingActivity)
                    startActivity(Intent(thisActivity, MainTabActivity::class.java))
                    thisActivity.finish()
                }
            }

            it.showPassLogin.setOnClickListener {
                this.toggleOn = !this.toggleOn
                ButtonUtils.showPasswordUtils(this.toggleOn, textLoginPassword, showPassLogin)
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

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}
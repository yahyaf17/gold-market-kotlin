package com.mandiri.goldmarket.login

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.maintab.MainTabActivity
import com.mandiri.goldmarket.utils.ButtonUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {

    var toggleOn by Delegates.notNull<Boolean>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_login)

        this.toggleOn = false
        btnLogin.isEnabled = false
        val textEdits = listOf(textLoginUsername, textLoginPassword)

        disableLoginBtn(textEdits)

        textToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        showPassLogin.setOnClickListener {
            this.toggleOn = !this.toggleOn
            ButtonUtils.showPasswordUtils(this.toggleOn, textLoginPassword, showPassLogin)
        }

        btnLogin.setOnClickListener {
//            if (textLoginUsername.text.toString() == "tes" && textLoginPassword.text.toString() == "enigma") {
                startActivity(Intent(this, MainTabActivity::class.java))
//            }
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
}
package com.mandiri.goldmarket.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.utils.ButtonUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {

    var toggleOn by Delegates.notNull<Boolean>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.toggleOn = false

        textToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        showPassLogin.setOnClickListener {
            this.toggleOn = !this.toggleOn
            ButtonUtils.showPasswordUtils(this.toggleOn, textLoginPassword, showPassLogin)
        }
    }
}
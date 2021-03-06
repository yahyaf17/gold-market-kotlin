package com.mandiri.goldmarket.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.utils.ButtonUtils
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    var toggleOn: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        textToLogin.setOnClickListener {
            onBackPressed()
        }

        showPassRegister.setOnClickListener {
            this.toggleOn = !this.toggleOn
            ButtonUtils.showPasswordUtils(this.toggleOn, textRegisterPassword, showPassRegister)
        }
    }
}
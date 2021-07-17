package com.mandiri.goldmarket.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.mandiri.goldmarket.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_welcome)

        btnGetStarted.setOnClickListener {
            val toLoginActivity = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(toLoginActivity)
        }
    }
}
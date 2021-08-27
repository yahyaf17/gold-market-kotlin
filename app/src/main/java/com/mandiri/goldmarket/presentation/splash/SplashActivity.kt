package com.mandiri.goldmarket.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.presentation.onboarding.onboard.OnboardingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
package com.mandiri.goldmarket.presentation.onboarding.onboard

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mandiri.goldmarket.databinding.ActivityOnboardingBinding
import com.mandiri.goldmarket.presentation.maintab.main.MainTabActivity
import com.mandiri.goldmarket.utils.CustomSharedPreferences


class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPrefInit = applicationContext.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)
        val sharedPref = CustomSharedPreferences(sharedPrefInit)
        val token = sharedPref.retrieveValue(CustomSharedPreferences.Key.TOKEN)
        if (token != null) {
            startActivity(Intent(this, MainTabActivity::class.java))
            this.finish()
        }
    }
}
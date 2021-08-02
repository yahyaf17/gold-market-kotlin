package com.mandiri.goldmarket.presentation.maintab.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mandiri.goldmarket.R
import com.mandiri.goldmarket.databinding.ActivityMainTabBinding

class MainTabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainTabBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.navigationBottom.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainTabActivity", "OnDestroy()")
    }
}
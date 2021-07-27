package com.mandiri.goldmarket.maintab

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
    lateinit var navController: NavController
    lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.navigationBottom.setupWithNavController(navController)

//        switchFragment(HomeFragment())
//        tabSelected(home_tab, history_tab, profile_tab)
//
//        btn_home.setOnClickListener {
//            tabSelected(home_tab, history_tab, profile_tab)
//            switchFragment(HomeFragment())
//        }
//
//        btn_profile.setOnClickListener {
//            tabSelected(profile_tab, history_tab, home_tab)
//            switchFragment(ProfileFragment())
//        }
//
//        btn_history.setOnClickListener {
//            tabSelected(history_tab, home_tab, profile_tab)
//            switchFragment(HistoryFragment())
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainTabActivity", "OnDestroy()")
    }

//    private fun switchFragment(fragment: Fragment) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragment_container, fragment).commit()
//    }
//
//    private fun tabSelected(selectedTab: View, unselectedTab1: View, unselectedTab2: View) {
//        selectedTab.apply {
//            this.setBackgroundColor(resources.getColor(R.color.gm_secondary))
//        }
//        unselectedTab1.apply {
//            this.setBackgroundColor(resources.getColor(R.color.gm_primary))
//        }
//
//        unselectedTab2.apply {
//            this.setBackgroundColor(resources.getColor(R.color.gm_primary))
//        }
//    }
}
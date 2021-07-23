package com.mandiri.goldmarket.maintab

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mandiri.goldmarket.R
import kotlinx.android.synthetic.main.activity_main_tab.*

class MainTabActivity : AppCompatActivity() {

//    private val primaryColor: Int = resources.getColor(R.color.gm_primary)
//    private val secondaryColor: Int = resources.getColor(R.color.gm_secondary)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tab)

        switchFragment(HomeFragment())
        tabSelected(home_tab, history_tab, profile_tab)

        btn_home.setOnClickListener {
            tabSelected(home_tab, history_tab, profile_tab)
            switchFragment(HomeFragment())
        }

        btn_profile.setOnClickListener {
            tabSelected(profile_tab, history_tab, home_tab)
            switchFragment(ProfileFragment())
        }

        btn_history.setOnClickListener {
            tabSelected(history_tab, home_tab, profile_tab)
            switchFragment(HistoryFragment())
        }
    }

    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).commit()
    }

    private fun tabSelected(selectedTab: View, unselectedTab1: View, unselectedTab2: View) {
        selectedTab.apply {
            this.setBackgroundColor(resources.getColor(R.color.gm_secondary))
        }
        unselectedTab1.apply {
            this.setBackgroundColor(resources.getColor(R.color.gm_primary))
        }

        unselectedTab2.apply {
            this.setBackgroundColor(resources.getColor(R.color.gm_primary))
        }
    }
}
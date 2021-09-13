package com.android.kumbar.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.kumbar.R
import com.android.kumbar.databinding.ActivityMainBinding
import com.android.kumbar.fragment.EquipmentFragment
import com.android.kumbar.fragment.HomeFragment
import com.android.kumbar.model.Mount
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {

        const val HOME_FRAGMENT_TAG = "home_fragment_tag"
        const val EQUIPMENT_FRAGMENT_TAG = "equipment_fragment_tag"

        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set transparent statusbar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.WHITE
        }

        val homeFragment = HomeFragment()
        val equipmentFragment = EquipmentFragment()
        if (savedInstanceState != null) {
            supportFragmentManager.findFragmentByTag(HOME_FRAGMENT_TAG)
                ?.let { setCurrentFragment(it, HOME_FRAGMENT_TAG) }
        } else {

            setCurrentFragment(homeFragment, HOME_FRAGMENT_TAG)
        }

        binding.navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.explore -> {
                    setCurrentFragment(homeFragment, HOME_FRAGMENT_TAG)
                }
                R.id.equipment -> {
                    setCurrentFragment(equipmentFragment, EQUIPMENT_FRAGMENT_TAG)
                }
            }
            true
        }
    }


    private fun setCurrentFragment(fragment: Fragment, fragmentTag: String) {
        supportFragmentManager.commit {
            replace(R.id.display_fragment, fragment, fragmentTag)
        }
    }
}




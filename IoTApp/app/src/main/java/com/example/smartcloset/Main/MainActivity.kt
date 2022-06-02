package com.example.smartcloset.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.smartcloset.Compare.Compare
import com.example.smartcloset.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val fl: FrameLayout by lazy {
        findViewById(R.id.fl_con)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bnv_main = findViewById<BottomNavigationView>(R.id.bnv_main)

        //supportFragmentManager.beginTransaction().add(R.id.fl_con, NaviHomeFragment()).commit()

        bnv_main.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.first -> {
                        bnv_main.itemIconTintList = ContextCompat.getColorStateList(this,
                            R.color.color_bnv1
                        )
                        bnv_main.itemTextColor = ContextCompat.getColorStateList(this,
                            R.color.color_bnv1
                        )
//                        frag1_txt.text = "ok"
                        Login("first ok")
                        // Respond to navigation item 1 click
                    }
                    R.id.second -> {
//                        bnv_main.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
//                        bnv_main.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        Register()
                        // Respond to navigation item 2 click
                    }
                    R.id.third -> {
//                        bnv_main.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
//                        bnv_main.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        FindId()
                        // Respond to navigation item 3 click
                    }
                    else -> {
//                        bnv_main.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv1)
//                        bnv_main.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        Compare()
                    }
                }
            )
            true
        }
        bnv_main.selectedItemId = R.id.first
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_con, fragment)
            .commit()
    }

}
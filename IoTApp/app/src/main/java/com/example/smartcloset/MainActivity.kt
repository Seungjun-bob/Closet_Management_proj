package com.example.smartcloset

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.smartcloset.add.AddClothesFragment
import com.example.smartcloset.compare.CompareFragment

import com.example.smartcloset.login.FIRSTBUTTON
import com.example.smartcloset.myPage.MyPage
import com.example.smartcloset.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

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
                        AddClothesFragment()
                        // Respond to navigation item 1 click
                    }
                    R.id.second -> {
//                        bnv_main.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
//                        bnv_main.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        AddClothesFragment()
                        // Respond to navigation item 2 click
                    }
                    R.id.third -> {
//                        bnv_main.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
//                        bnv_main.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        AddClothesFragment()
                        // Respond to navigation item 3 click
                    }
                    else -> {
//                        bnv_main.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv1)
//                        bnv_main.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        CompareFragment()

                    }
                }
            )
            true
        }
        bnv_main.selectedItemId = R.id.first
        //마이페이지 버튼
        mypage.setOnClickListener {
            val intent = Intent(this, MyPage::class.java).apply {    // 괄호 안에 있는 MyPage수정하세요!
            }
            startActivityForResult(intent, FIRSTBUTTON)
        }
    }



    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_con, fragment)
            .commit()
    }

}
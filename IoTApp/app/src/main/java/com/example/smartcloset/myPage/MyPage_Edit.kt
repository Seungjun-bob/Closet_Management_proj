package com.example.smartcloset.myPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.smartcloset.MainActivity
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.mypage.*
import com.example.smartcloset.login.userId //로그인 하면서 받아온 userId전역변수


// btn save 하면 다시 홈으로 돌아오기 추가해야 함.

class MyPage_Edit : AppCompatActivity() {
    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage_edit)
    }
}

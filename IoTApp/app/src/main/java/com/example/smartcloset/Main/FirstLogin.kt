package com.example.smartcloset.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.login.*

class FirstLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        login.setOnClickListener{
            Toast.makeText(this,"로그인 버튼입니다",Toast.LENGTH_SHORT).show()
        }
        findid.setOnClickListener{
            Toast.makeText(this,"ID찾기 버튼입니다",Toast.LENGTH_SHORT).show()
        }
        findpw.setOnClickListener{
            Toast.makeText(this,"PW찾기 버튼입니다",Toast.LENGTH_SHORT).show()
        }
        register.setOnClickListener{
            Toast.makeText(this,"회원가입 버튼입니다",Toast.LENGTH_SHORT).show()
        }
    }

}
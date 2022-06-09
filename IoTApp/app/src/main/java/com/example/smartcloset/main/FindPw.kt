package com.example.smartcloset.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.findpw.*

class FindPw: AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.findpw)

        submit_findpw.setOnClickListener(this)
        back_findpw.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.submit_findpw -> {

            }
            R.id.back_findpw -> {
                val intent = Intent(this, FirstLogin::class.java).apply {
                }
                startActivityForResult(intent, FIRSTBUTTON)
            }
        }
    }

}
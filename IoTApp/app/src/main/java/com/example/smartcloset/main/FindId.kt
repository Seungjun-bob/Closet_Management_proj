package com.example.smartcloset.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.findid.*

class FindId: AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.findid)

        submit_findid.setOnClickListener(this)
        back_findid.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.submit_findid -> {

            }
            R.id.back_findid -> {
                val intent = Intent(this, FirstLogin::class.java).apply {
                }
                startActivityForResult(intent, FIRSTBUTTON)
            }
        }
    }
}
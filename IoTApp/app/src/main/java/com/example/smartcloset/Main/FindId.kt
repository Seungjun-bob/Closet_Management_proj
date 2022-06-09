package com.example.smartcloset.Main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.smartcloset.R
import com.example.smartcloset.network.MyMqtt
import kotlinx.android.synthetic.main.findid.*
import kotlinx.android.synthetic.main.findpw.*
import kotlinx.android.synthetic.main.login.*
import org.eclipse.paho.client.mqttv3.MqttMessage

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
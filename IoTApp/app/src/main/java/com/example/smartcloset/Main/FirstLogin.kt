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
import kotlinx.android.synthetic.main.login.*
import org.eclipse.paho.client.mqttv3.MqttMessage

const val FIRSTBUTTON = 10

class FirstLogin: AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        login.setOnClickListener(this)
        findid.setOnClickListener(this)
        findpw.setOnClickListener(this)
        register.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        var data: String = ""
        when(v?.id){
            R.id.login -> {}
            R.id.findid -> {
                val intent = Intent(this, FindId::class.java).apply{
                }
                startActivityForResult(intent, FIRSTBUTTON)
            }
            R.id.findpw -> {
                val intent = Intent(this, FindPw::class.java).apply{
                }
                startActivityForResult(intent, FIRSTBUTTON)
            }
            R.id.register -> {
                val intent = Intent(this, Register::class.java).apply{
                }
                startActivityForResult(intent, FIRSTBUTTON)
            }
        }
    }


}
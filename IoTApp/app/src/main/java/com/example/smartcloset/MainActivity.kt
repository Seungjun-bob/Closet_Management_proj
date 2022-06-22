package com.example.smartcloset

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.smartcloset.about.AboutFragment
import com.example.smartcloset.add.AddClothesFragment
import com.example.smartcloset.check.Check
import com.example.smartcloset.compare.CompareFragment

import com.example.smartcloset.login.FIRSTBUTTON
import com.example.smartcloset.myPage.MyPage
import com.example.smartcloset.home.HomeFragment
import com.example.smartcloset.network.MyMqtt
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home.*
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

class MainActivity : AppCompatActivity() {
    // mqtt
    val sub_topic = "iot/sensordata"
    val server_uri = "tcp://54.212.177.89:1883" //broker의 ip와 port 54.187.211.80
    var mymqtt : MyMqtt? = null


    val fl: FrameLayout by lazy {
        findViewById(R.id.fl_con)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Mqtt통신을 수행항 Mqtt객체를 생성
        mymqtt = MyMqtt(this,server_uri)
        //브로커에서 메시지 전달되면 호출될 메소드를 넘기기
        mymqtt?.mysetCallback(::onReceived)
        //브로커연결
        mymqtt?.connect(arrayOf<String>(sub_topic)) //

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
                        HomeFragment()
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
                        Check()
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
    private fun loadModelFile(): ByteBuffer{
        val assetFileDescriptor = this.assets.openFd("simple_5_classfication-fp16.tflite")
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val length = assetFileDescriptor.length
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, length)
    }
    fun onReceived(topic:String,message: MqttMessage){
        //토픽의 수신을 처리
        var msg = String(message.payload).split(':')
        dust_status?.text = msg[1]
        hum_status?.text = msg[3]
        temp_status?.text = msg[5]

    }
}
package com.example.smartcloset

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.smartcloset.about.AboutFragment
import com.example.smartcloset.add.AddClothesFragment
import com.example.smartcloset.add.AddClothesFragment_ver3
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
import com.example.smartcloset.login.userId //로그인 하면서 받아온 userId전역변수



var auth_cnt = 0
class MainActivity : AppCompatActivity() {


    // mqtt
    val sub_topic = "iot/#"

    val server_uri = "tcp://35.84.212.137:1883" //broker의 ip와 port
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
                        AddClothesFragment_ver3()
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
        var msg = String(message.payload)
        // 메시지가 sensorerror면
        if(msg=="sensorerror"){
            var noti_builder = NotificationCompat.Builder(this, "MY_channel")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("센서 이상 감지")
                    .setContentText("센서 데이터에 이상이 있습니다.")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 오레오 버전 이후에는 알림을 받을 때 채널이 필요
                val channel_id = "MY_channel" // 알림을 받을 채널 id 설정
                val channel_name = "채널이름" // 채널 이름 설정
                val descriptionText = "설명글" // 채널 설명글 설정
                val importance = NotificationManager.IMPORTANCE_DEFAULT // 알림 우선순위 설정
                val channel = NotificationChannel(channel_id, channel_name, importance).apply {
                    description = descriptionText
                }

                // 만든 채널 정보를 시스템에 등록
                val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)

                // 알림 표시: 알림의 고유 ID(ex: 1002), 알림 결과
                notificationManager.notify(1002, noti_builder.build())
            }

        }else {
            var data = msg.split(':')
            if(data[0] == "Dust Density [ug/m3]") {
                dust_status?.text = data[1]
                hum_status?.text = data[3]
                temp_status?.text = data[5]
            }else{

            }
        }
    }
    open fun changeFragment(index: Int){
        when(index){
            1 -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fl_con, HomeFragment())
                        .commit()
            }

        }
    }
}
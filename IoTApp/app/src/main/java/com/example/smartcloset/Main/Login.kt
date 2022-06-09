package com.example.smartcloset.Main

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

class Login: AppCompatActivity(), View.OnClickListener {
    val sub_topic = "login/result"
    val server_uri = "tcp://192.168.200.107:1883" //broker의 ip와 port
    var mymqtt: MyMqtt? = null
    lateinit var msg:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        //Mqtt통신을 수행항 Mqtt객체를 생성
        mymqtt = MyMqtt(this, server_uri)
        //브로커에서 메시지 전달되면 호출될 메소드를 넘기기
        mymqtt?.mysetCallback(::onReceived)
        //브로커연결
        mymqtt?.connect(arrayOf<String>(sub_topic)) //

        login.setOnClickListener(this)
    }
    fun onReceived(topic:String, message: MqttMessage) {
        //토픽의 수신을 처리
        //예)EditText의 내용을 출력하기, 영상출력, ... 도착된 메시지 안에서 온도 습도 데이터를 이용해서 차트 그리기, 모션 detect 알림 등...
        msg = String(message.payload)
    }

    override fun onClick(v: View?) {
        var data: String = ""
        if(v?.id== R.id.login) {
            data = "${id.text}, ${pw.text}"
            mymqtt?.publish("login",data)
            // 아이디 비밀번호 일치하는지 확인하는 과정이 들어가고....
            if(msg=="OK") {
                Toast.makeText(this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                //로그인 이후로 넘어가기
            }else{
                Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
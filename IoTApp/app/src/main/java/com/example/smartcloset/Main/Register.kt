package com.example.smartcloset.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.smartcloset.R
import com.example.smartcloset.network.MyMqtt
import kotlinx.android.synthetic.main.register.*
import org.eclipse.paho.client.mqttv3.MqttMessage

class Register: AppCompatActivity(), View.OnClickListener {
    val sub_topic = "register/result"
    val server_uri = "tcp://192.168.0.2:1883" //broker의 ip와 port
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

        submit_register.setOnClickListener(this)
    }
    fun onReceived(topic:String, message: MqttMessage) {
        //토픽의 수신을 처리
        //예)EditText의 내용을 출력하기, 영상출력, ... 도착된 메시지 안에서 온도 습도 데이터를 이용해서 차트 그리기, 모션 detect 알림 등...
        msg = String(message.payload)
    }

    override fun onClick(v: View?) {
        var data: String = ""
        if(v?.id== R.id.submit_register) {
            if (pw_register.text == pwcheck_register.text) {
                data = "${id_register.text}, ${pw_register.text}, ${name_register.text}, ${birthday_register.text}"
                mymqtt?.publish("register",data)
                if(msg=="OK") {
                    Toast.makeText(this, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                    //돌아가는 기능 추가하기
                }else{
                    Toast.makeText(this, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show()
                    //잘못된거 표시..?
                }
            }
        }
    }
}

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
import kotlinx.android.synthetic.main.findid.*
import kotlinx.android.synthetic.main.findpw.*
import kotlinx.android.synthetic.main.login.*
import org.eclipse.paho.client.mqttv3.MqttMessage

class FindId: AppCompatActivity(), View.OnClickListener {
    val sub_topic = "findid/result"
    val server_uri = "tcp://192.168.200.107:1883" //broker의 ip와 port
    var mymqtt: MyMqtt? = null
    lateinit var msg:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.findid)
        //Mqtt통신을 수행항 Mqtt객체를 생성
        mymqtt = MyMqtt(this, server_uri)
        //브로커에서 메시지 전달되면 호출될 메소드를 넘기기
        mymqtt?.mysetCallback(::onReceived)
        //브로커연결
        mymqtt?.connect(arrayOf<String>(sub_topic)) //

        submit_findid.setOnClickListener(this)
        back_findid.setOnClickListener(this)

    }
    fun onReceived(topic:String, message: MqttMessage) {
        //토픽의 수신을 처리
        //예)EditText의 내용을 출력하기, 영상출력, ... 도착된 메시지 안에서 온도 습도 데이터를 이용해서 차트 그리기, 모션 detect 알림 등...
        msg = String(message.payload)
    }

    override fun onClick(v: View?) {
        var data: String = ""
        if(v?.id== R.id.submit_findid) {
            data = "${name_findid.text}, ${birthday_findid.text}"
            mymqtt?.publish("findid",data)
            // 확인하는 과정이 들어가고.... 아이디 데이터 받아서
            if(msg=="OK") {
                show_findid.text = "ID는 () 입니다"
            }else{
                Toast.makeText(this, "회원정보를 확인하세요", Toast.LENGTH_SHORT).show()
            }
        }else if(v?.id==R.id.back_findid){
            Toast.makeText(this, "돌아가는 버튼 구현하기", Toast.LENGTH_SHORT).show()
        }
    }

}
package com.example.smartcloset.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.activity_main.*
import org.eclipse.paho.client.mqttv3.MqttMessage

//화면디자인 - 화면에 있는 위젯들의 이벤트에 반응하는 처리만 구현
class MqttTestActivity : AppCompatActivity(), View.OnClickListener {
    val sub_topic = "iot/#"
    val server_uri = "tcp://192.168.200.107:1883" //broker의 ip와 port
    var mymqtt : MyMqtt? = null
    var auto = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Mqtt통신을 수행항 Mqtt객체를 생성
        mymqtt = MyMqtt(this,server_uri)
        //브로커에서 메시지 전달되면 호출될 메소드를 넘기기
        mymqtt?.mysetCallback(::onReceived)
        //브로커연결
        mymqtt?.connect(arrayOf<String>(sub_topic)) //
        //이벤트 연결
//        roll_down.setOnClickListener(this)
//        roll_up.setOnClickListener(this)
//        switch1.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var data:String = ""
//        if(v?.id== R.id.roll_down) {
//            if (auto == true) {
//                Toast.makeText(this, "자동모드입니다", Toast.LENGTH_LONG).show()
//            } else {
//                data = "roll_down"
//                Toast.makeText(this, "블라인드를 내립니다.", Toast.LENGTH_LONG).show()
//            }
//        }else if(v?.id== R.id.roll_up){
//            if (auto == true) {
//                Toast.makeText(this, "자동모드입니다", Toast.LENGTH_LONG).show()
//            } else {
//                data = "roll_up"
//                Toast.makeText(this, "블라인드를 올립니다.", Toast.LENGTH_LONG).show()
//            }
//        }else if (v?.id== R.id.switch1){
//            if(auto == false){
//                data = "auto_on"
//                auto = true
//                Toast.makeText(this,"자동모드를 실행합니다.",Toast.LENGTH_LONG).show()
//            }else{
//                data = "auto_off"
//                auto = false
//                Toast.makeText(this,"자동모드를 종료합니다.",Toast.LENGTH_LONG).show()
//            }
//        }
        mymqtt?.publish("iot/blind",data)
    }
    //액티비티 내부에 디자인된 위젯을 액세스해야하므로 외부 클래스에 있으면 액티비티의 구성요소를 접근하기 위해서 액티비티를 넘겨주어야하는 번거로움을 없애기 위해
    //액티비티 내부에 메소드를 정의
    fun onReceived(topic:String,message:MqttMessage){
        //토픽의 수신을 처리
        //예)EditText의 내용을 출력하기, 영상출력, ... 도착된 메시지 안에서 온도 습도 데이터를 이용해서 차트 그리기, 모션 detect 알림 등...
        val msg = String(message.payload)
//        val msgdata = msg.split(":")
//        when(msgdata[0]){
//            "humidity"-> showdata.append("습도: "+msgdata[1]+"\n")
//            "temperature"-> showdata.append("온도: "+msgdata[1]+"\n")
//            "pir"->{
//                if(msgdata[1]=="motion detected"){
//                    showdata.append("침입자발생\n")
//                }else{
//                    showdata.append("침입자없음\n")
//                }
//            }
//        }
//        showdata.append(msgdata[0]+"침입자발생\n")
        Log.d("mymqtt",msg)
    }
}
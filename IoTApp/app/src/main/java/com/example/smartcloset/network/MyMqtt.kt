package com.example.smartcloset.network

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MyMqtt(context: Context, uri:String){
    //안드로이드에서 mqtt통신을 수행할 객체를 생성 - MqttAndroidClient
    var mqttClient:MqttAndroidClient = MqttAndroidClient(context,uri, MqttClient.generateClientId())
    //메시지가 전송되면 callback메소드가 호출될 수 있도록 등록 -----------------------------3)
    //액티비티에서 mysetCallback 메소드를 호출하면서 실행할 메소드를 넘기면 MqttAndroidclient객체의 setCallback메소드가 호출되며
    //콜백메소드가 등록된다.
    fun mysetCallback(callback: (topic:String,message:MqttMessage)->Unit){
        mqttClient.setCallback(object : MqttCallback{
            override fun connectionLost(cause: Throwable?) {
                Log.d("mymqtt","connectionLost")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                //메시지가 전송되면 호출 - 액티비티의 메소드
                //메시지가 전송되면 처리할 일들을 여기서 구현해도 되는데 - 액티비티의 구성요소에 메시지로 전달되는 내용을 출력하거나 사용하고
                //싶어서 액티비티에 구현한 메소드를 호출할 예정
                Log.d("mymqtt","message arrived")
                callback(topic!!,message!!)
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.d("mymqtt","delivery")
            }

        })
    }
    //mqtt통신을 하기 위해 브로커서버와 연결, 연결이 끝난 후 콜백메소드 설정
    fun connect(topic:Array<String>){
        //연결하기 위해서 필요한 여러가지 정보를 담고 있는 객체
        val mqttConnectOptions = MqttConnectOptions()
        //MqttAndroidClient객체의 connect를 호출하며 브로커에 연결을 시도
        //안드로이드 내부에서 브로커에 연결을 성공하면 자동으로 이벤트가 발생하며 이를 처리하는 리스너가 IMqttActionListener
        mqttClient.connect(mqttConnectOptions,null,
                                                object:IMqttActionListener{
                                                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                                                        //접속 성공
                                                        Log.d("mymqtt","브로커 접속성공...")
                                                        //메시지 접속 성공하면 subscribe하기----------------------2)
                                                        //topic을 여러개 등록할 수 있으므로 배열처리
                                                        //모든 전달된 토픽을 subscribe
                                                        //for문이나 if문으로 처리할 수 있는데 map함수를 이용하면 편하게 작업 가능
                                                        topic.map {
                                                            subscribeTopic(it)
                                                        }
                                                    }

                                                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?){
                                                        //접속실패
                                                        Log.d("mymqtt","브로커 접속실패...")
                                                    }

                                                })

    }
    //토픽을 subscribe 등록하기 위해서 메소드 구현 ----------------1)
    private fun subscribeTopic(topic:String, qos:Int=0){
        mqttClient.subscribe(topic, qos,null,object:IMqttActionListener{
                                                                override fun onSuccess(asyncActionToken: IMqttToken?) {
                                                                    Log.d("mymqtt","+++메시지 전송성공...")
                                                                }

                                                                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                                                                    Log.d("mymqtt","+++메시지 전송실패...")
                                                                }
        })
    }

    fun publish(topic:String,payload:String,qos:Int=0){
        if(mqttClient.isConnected === false){
            mqttClient.connect()
        }
        //mqtt로 전송할 메시지 객체를 생성
        val message = MqttMessage()
        //메시지객체에 payload와 메시지 전송품질을 설정
        //네트워크로 전송되므로 byte로 변경
        message.payload = payload.toByteArray()
        message.qos = qos
        //메시지 전송하기(publish) - publish가 성공/실패하는 경우 이벤트가 발생하기 때문에 리스너 등록
        //mqttClient.publish(topic,message) - 이 명령문처럼 publish해도 좋음
        //publish후 콜백이 실행되도록 하고 싶다면
        mqttClient.publish(topic,message,null,object:IMqttActionListener{
                                                            override fun onSuccess(asyncActionToken: IMqttToken?) {
                                                                Log.d("mymqtt","+++메시지 전송성공...")
                                                            }

                                                            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                                                                Log.d("mymqtt","+++메시지 전송실패...")
                                                            }
        })
    }
}
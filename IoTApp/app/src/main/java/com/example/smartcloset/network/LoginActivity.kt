package com.example.smartcloset.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.json.JSONObject
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login.setOnClickListener{
            thread{
                //서버로 전송할 JSONObject 만들기 - 사용자가 입력한 id와 password를 담고 있음
                var jsonobj = JSONObject()
                jsonobj.put("boardNo",id.text)
                jsonobj.put("writer",pass.text)
                val url = "http://192.168.200.107:8000/login"

                //Okhttp3라이브러리의 OkHttpClient객체를 이요해서 작업
                val client = OkHttpClient()

                //json데이터를 이용해서 request 처리
                val jsondata = jsonobj.toString()
                //서버에 요청을 담당하는 객체
                val builder = Request.Builder()    // request객체를 만들어주는 객체 생성
                builder.url(url)                   //Builder객체에 request할 주소(네트워크상의 주소)셋팅
                builder.post(RequestBody.create(MediaType.parse("application/json"),jsondata)) // 요청메시지 만들고 요청메시지의 타입이 json이라고 설정
                val myrequest:Request = builder.build() //Builder객체를 이용해서 request객체 만들기
                //생성한 request 객체를 이용해서 웹에 request하기 - request결과로 response 객체가 리턴
                val response:Response = client.newCall(myrequest).execute()

                //response에서 메시지꺼내서 로그 출력하기
                val result:String? = response.body()?.string()
                Log.d("http",result!!)
                //로그인 성공여부가 메시지로 전달되면 그에 따라 다르게 작업할 수 있도록 코드



//                val request:Request = Request.Builder()
//                    .url(url)
//                    .post(RequestBody.create(MediaType.parse("application/json"),jsondata))
//                    .build()
            }
        }
    }
}
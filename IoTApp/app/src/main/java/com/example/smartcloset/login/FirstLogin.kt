package com.example.smartcloset.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.login.*
import okhttp3.*
import org.json.JSONObject
import kotlin.concurrent.thread

const val FIRSTBUTTON = 10

class FirstLogin: AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        findid.setOnClickListener(this)
        findpw.setOnClickListener(this)
        register.setOnClickListener(this)
        login.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        var data: String = ""
        when(v?.id){
            R.id.login -> {
                thread{
                    //서버로 전송할 JSONObject 만들기 - 사용자가 입력한 id와 password를 담고 있음
                    var jsonobj = JSONObject()
                    jsonobj.put("ID",id.text)
                    jsonobj.put("PW",pw.text)
                    val url = "http://192.168.200.107:8000/login"

                    //Okhttp3라이브러리의 OkHttpClient객체를 이요해서 작업
                    val client = OkHttpClient()

                    //json데이터를 이용해서 request 처리
                    val jsondata = jsonobj.toString()
                    //서버에 요청을 담당하는 객체
                    val builder = Request.Builder()    // request객체를 만들어주는 객체 생성
                    builder.url(url)                   //Builder객체에 request할 주소(네트워크상의 주소)셋팅
                    builder.post(RequestBody.create(MediaType.parse("application/json"),jsondata)) // 요청메시지 만들고 요청메시지의 타입이 json이라고 설정
                    val myrequest: Request = builder.build() //Builder객체를 이용해서 request객체 만들기
                    //생성한 request 객체를 이용해서 웹에 request하기 - request결과로 response 객체가 리턴
                    val response: Response = client.newCall(myrequest).execute()

                    //response에서 메시지꺼내서 로그 출력하기
                    val result:String? = response.body()?.string()
                    Log.d("http",result!!)
                    //로그인 성공여부가 메시지로 전달되면 그에 따라 다르게 작업할 수 있도록 코드


                }
            }
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
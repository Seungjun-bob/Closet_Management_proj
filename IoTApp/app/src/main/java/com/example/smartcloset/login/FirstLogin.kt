package com.example.smartcloset.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcloset.R
import kotlinx.android.synthetic.main.findpw.*
import kotlinx.android.synthetic.main.login.*
import okhttp3.*
import org.json.JSONObject
import kotlin.concurrent.thread

const val FIRSTBUTTON = 10

class FirstLogin: AppCompatActivity(), View.OnClickListener {
    val TAG: String = "Login"

    var isExistBlank = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        findid.setOnClickListener(this)
        findpw.setOnClickListener(this)
        register.setOnClickListener(this)
        login.setOnClickListener(this)

    }
    //*************다이얼로그 메서드**********
    fun dialog(type: String){
        val dialog = AlertDialog.Builder(this)
        // 빈 칸 있을 경우
        if(type.equals("blank")){
            dialog.setTitle("조회 실패")
            dialog.setMessage("입력란을 모두 작성해주세요")
        }

        val dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(TAG, "다이얼로그")
                }
            }
        }
        dialog.setPositiveButton("확인",dialog_listener)
        dialog.show()
    }


    override fun onClick(v: View?) {
        var data: String = ""
        when(v?.id){
            R.id.login -> {
                thread {
                    var id = id_login.text.toString()
                    var pw = pw_login.text.toString()

                    // 유저가 항목을 다 채우지 않았을 경우
                    if (id.isEmpty() || pw.isEmpty()) {
                        isExistBlank = true
                    } else {
                        isExistBlank = false
                    }
                    if (!isExistBlank) {

                        //서버로 전송할 JSONObject 만들기 - 사용자가 입력한 id와 password를 담고 있음
                        var jsonobj = JSONObject()
                        jsonobj.put("ID", id)
                        jsonobj.put("PW", pw)
                        val url = "http://192.168.200.107:8000/login" // 장고 로그인페이지 url

                        //Okhttp3라이브러리의 OkHttpClient객체를 이요해서 작업
                        val client = OkHttpClient()

                        //json데이터를 이용해서 request 처리
                        val jsondata = jsonobj.toString()
                        //서버에 요청을 담당하는 객체
                        val builder = Request.Builder()    // request객체를 만들어주는 객체 생성
                        builder.url(url)                   //Builder객체에 request할 주소(네트워크상의 주소)셋팅
                        builder.post(
                            RequestBody.create(
                                MediaType.parse("application/json"),
                                jsondata
                            )
                        ) // 요청메시지 만들고 요청메시지의 타입이 json이라고 설정
                        val myrequest: Request = builder.build() //Builder객체를 이용해서 request객체 만들기
                        //생성한 request 객체를 이용해서 웹에 request하기 - request결과로 response 객체가 리턴
                        val response: Response = client.newCall(myrequest).execute()

                        //response에서 메시지꺼내서 로그 출력하기
                        val result: String? = response.body()?.string()
                        Log.d("http", result!!)
                        //로그인 성공여부가 메시지로 전달되면 그에 따라 다르게 작업할 수 있도록 코드
                        runOnUiThread {
                            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // 상태에 따라 다른 다이얼로그 띄워주기
                        if (isExistBlank) {   // 작성 안한 항목이 있을 경우
                            runOnUiThread {
                                dialog("blank")
                            }
                        }
                    }
                }

            }
            R.id.findid -> {
                val intent = Intent(this, FindId::class.java).apply{
                }
                startActivity(intent)
            }
            R.id.findpw -> {
                val intent = Intent(this, FindPw::class.java).apply{
                }
                startActivity(intent)
            }
            R.id.register -> {
                val intent = Intent(this, Register::class.java).apply{
                }
                startActivity(intent)
            }
        }
    }


}